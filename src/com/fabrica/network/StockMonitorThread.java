package com.fabrica.network;

import com.fabrica.exception.PersistenciaException;
import com.fabrica.exception.TcpAlertException;
import com.fabrica.io.AlertLogRepository;
import com.fabrica.model.ItemSnapshot;
import com.fabrica.model.StockAlert;
import com.fabrica.service.InventoryService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StockMonitorThread extends Thread {
    private final InventoryService inventoryService;
    private final TcpAlertClient tcpAlertClient;
    private final AlertLogRepository alertLogRepository;
    private final long intervalMillis;
    private final Set<String> criticalItemsAlreadyNotified;
    private volatile boolean running;

    public StockMonitorThread(InventoryService inventoryService,
                              TcpAlertClient tcpAlertClient,
                              AlertLogRepository alertLogRepository,
                              long intervalMillis) {
        super("stock-monitor-thread");
        this.inventoryService = inventoryService;
        this.tcpAlertClient = tcpAlertClient;
        this.alertLogRepository = alertLogRepository;
        this.intervalMillis = intervalMillis;
        this.criticalItemsAlreadyNotified = new HashSet<String>();
        this.running = true;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (running) {
            try {
                monitorCriticalStock();
                Thread.sleep(intervalMillis);
            } catch (InterruptedException exception) {
                if (!running) {
                    return;
                }
                Thread.currentThread().interrupt();
                return;
            } catch (PersistenciaException exception) {
                System.err.println("Falha ao registrar alerta de estoque: " + exception.getMessage());
            }
        }
    }

    public void shutdown() {
        running = false;
        interrupt();
    }

    private void monitorCriticalStock() throws PersistenciaException {
        List<ItemSnapshot> snapshot = inventoryService.listarEstoque();
        Set<String> currentCriticalCodes = new HashSet<String>();

        for (ItemSnapshot item : snapshot) {
            if (!item.isCritical()) {
                continue;
            }

            currentCriticalCodes.add(item.getCodigo());
            if (criticalItemsAlreadyNotified.contains(item.getCodigo())) {
                continue;
            }

            StockAlert alert = StockAlert.fromSnapshot(item);
            try {
                tcpAlertClient.sendAlert(alert);
                alertLogRepository.appendAlert(alert.formatForDisplay());
            } catch (TcpAlertException exception) {
                alertLogRepository.appendAlert(
                        alert.formatForDisplay() + " Falha no envio TCP: " + exception.getMessage()
                );
            }

            criticalItemsAlreadyNotified.add(item.getCodigo());
        }

        criticalItemsAlreadyNotified.retainAll(currentCriticalCodes);
    }
}
