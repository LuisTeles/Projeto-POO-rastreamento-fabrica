package com.fabrica;

import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        executarMenu();
    }

    private static void executarMenu() {
        int opcao;

        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("=== FABRILOOP - CONTROLE DE ESTOQUE ===");
        System.out.println("1. 📦 Cadastrar Nova Matéria-Prima");
        System.out.println("2. 🏭 Iniciar Produção (Consumir Lote)");
        System.out.println("3. 🚚 Despachar Produto Acabado");
        System.out.println("4. 🔍 Rastrear Item por Código");
        System.out.println("5. ⚠️ Visualizar Alertas do Sistema");
        System.out.println("0. Sair");
        System.out.println("=======================================");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        while (!SCANNER.hasNextInt()) {
            System.out.print("Opção inválida. Digite um número do menu: ");
            SCANNER.nextLine();
        }

        int opcao = SCANNER.nextInt();
        SCANNER.nextLine();
        return opcao;
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                System.out.println("Cadastro de nova matéria-prima selecionado.");
                break;
            case 2:
                System.out.println("Início de produção selecionado.");
                break;
            case 3:
                System.out.println("Despacho de produto acabado selecionado.");
                break;
            case 4:
                System.out.println("Rastreamento por código selecionado.");
                break;
            case 5:
                System.out.println("Visualização de alertas selecionada.");
                break;
            case 0:
                System.out.println("Encerrando o sistema...");
                break;
            default:
                System.out.println("Opção inexistente. Tente novamente.");
                break;
        }
    }
}
