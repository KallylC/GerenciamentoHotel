package GerenciamentoHotelProjeto;

import javax.swing.JOptionPane;

import GerenciamentoHotelProjeto.VIEW.ViewManager;

public class Main {
     public static void main(String[] args) {
        boolean running = true;
        ViewManager viewManager = new ViewManager();

        JOptionPane.showMessageDialog(null, "Bem vindo! Voce está no sistema do Hotel K!", "Hotel K", 1);   
        while (running){
            int menuInput = 0;
            String[] menu = { "Quartos", "Reservas", "Hóspedes", "Funcionários", "Check-in/out", "Sair"};
            menuInput = JOptionPane.showOptionDialog(null, "Escolha uma opção", "Hotel K - Menu", 0,  3, null, menu, menu[0]);
    
            switch(menuInput){
                case 0:                
                    viewManager.exibirMenuQuartos();    
                    break;
                case 1:
                    viewManager.exibirMenuReserva();
                    break;
                case 2:
                    viewManager.exibirMenuHospede();
                    break;
                case 3:
                    viewManager.exibirMenuFuncionario();
                    break;
                case 4:
                    viewManager.exibirMenuCheckin();
                    break;
                case 5:
                    running = false;
            }
        }
           
        System.exit(0); // Encerra o programa
    }
}

