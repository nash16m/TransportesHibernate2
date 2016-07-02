/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anncode.transporteshibernate.main;

import com.anncode.transporteshibernate.Camion.Camion;
import com.anncode.transporteshibernate.Camion.CamionDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author GATITOS
 */
public class principal2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	/*SessionFactory sessionFactory;
	Configuration configuration = new Configuration();
	configuration.configure();
	sessionFactory = configuration.buildSessionFactory();*/
	Session session = null;//sessionFactory.openSession();
        muestraMenuInicio(session); 
    }

    private static void mostrarListaPorFiltro(Scanner entradaEscaner, Session session) {
        HashMap<String, Object>  parametros = null;
        
        parametros = imprimeParametros(entradaEscaner);
        
        //Realizar consulta
        CamionDAO camionDAO = new CamionDAO();
        List<Camion> listaCamiones = null;//camionDAO.obtenerRegistrosParametros(parametros, session);
                
        if(listaCamiones != null){
            System.out.println("ID        MATRICULA          MODELO");
            for(Camion camion : listaCamiones){
             System.out.println(camion.getId() + "          "+ camion.getMatricula() + "           " + camion.getModelo());
            }
            System.out.println("\nIngresa ID de camión para más detalle:");
            String idCamion = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanne

            mostrarDatosCamion(idCamion, entradaEscaner, session);
        }else{
            System.out.println("\nNo se encontró información\n");
        }
    }

    private static void mostrarDatosCamion(String idCamion, Scanner entradaEscaner,
            Session session) {
        //Buscar camion por id
        CamionDAO camionDAO = new CamionDAO();
        Camion camion = null;//camionDAO.obtenerRegistroPorID(new Integer(idCamion), session);
        if(camion != null){
            System.out.println("Datos de camion:");
            System.out.println("ID:" + camion.getId());
            System.out.println("MATRICULA:" + camion.getMatricula());
            System.out.println("MODELO:" + camion.getModelo());

            System.out.println("\n¿Qué desea hacer con el camión?");
            System.out.println("1 - Editar");
            System.out.println("2 - Eliminar");
            System.out.println("3 - Salir");
        }else{
            System.out.println("\nNo se encontraron datos del camión");
            System.out.println("\n3 - Salir");
        }
        
        String opcion = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanne
        Integer opcionInt = new Integer(opcion);
        switch(opcionInt){
            case 1:
                //registro editado
                System.out.println("Registro editado");
                break;
            case 2:
                //registro eliminado
                camionDAO.eliminar(camion, session);
                System.out.println("Registro eliminado");
                break;
            case 3:
                System.exit(0);
                break;
        }
        
    }

    private static void muestraMenuInicio(Session session) {
        System.out.println("Seleccione la opción deseada:");
        System.out.println("1 - Mostrar lista por filtro");
        System.out.println("2 - Editar varios camiones por filtro");
        System.out.println("3 - Eliminar varios camiones por filtro");
        System.out.println("4 - Salir\nOpcion:");
        
        String entradaTeclado ="";
        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanne
        
        if(entradaTeclado != null && !entradaTeclado.equals("")){
            Integer valorOp = new Integer(entradaTeclado);
           switch(valorOp){
               case 1:
                   mostrarListaPorFiltro(entradaEscaner, session);
                   muestraMenuInicio(session);
                   break;
               case 2:
                   editarCamionesPorFiltro(entradaEscaner, session);
                   muestraMenuInicio(session);
                   break;
               case 3:
                   eliminarCamionesPorFiltro(entradaEscaner, session);
                   muestraMenuInicio(session);
                   break;
               case 4:
                   System.exit(0);
                   break;
               default:
                   break;
            }
        }
    }

    private static HashMap<String, Object> imprimeParametros(Scanner entradaEscaner) {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        String matricula = "";
        String modelo = "";
        String tipo = "";
	String potencia = "";
        
        System.out.println("Matricula:");
        matricula = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanne
        System.out.println("Modelo:");
        modelo = entradaEscaner.nextLine ();
        System.out.println("Tipo:");
        tipo = entradaEscaner.nextLine ();
        System.out.println("Potencia:");
        potencia = entradaEscaner.nextLine ();
        
        if(matricula != null && !matricula.trim().equals("")){
            parametros.put("matricula", matricula);
        }
        
        if(modelo != null && !modelo.trim().equals("")){
            parametros.put("modelo", modelo);
        }
        
        if(tipo != null && !tipo.trim().equals("")){
            parametros.put("tipo", tipo);
        }
        
        if(potencia != null && !potencia.trim().equals("")){
            parametros.put("potencia", potencia);
        }
        
        return parametros;
    }

    private static void editarCamionesPorFiltro(Scanner entradaEscaner, Session session) {
        HashMap<String, Object>  parametros = null;
        HashMap<String, Object>  filtros = null;
        System.out.println("Seleccione el filtro para modificar camiones:\n");
        filtros = imprimeParametros(entradaEscaner);
        
        //Realizar consulta
        CamionDAO camionDAO = new CamionDAO();
        List<Camion> listaCamiones = camionDAO.obtenerRegistrosParametros(parametros, session);
        
        if(listaCamiones != null){
            System.out.println("ID        MATRICULA          MODELO");
            for(Camion camion : listaCamiones){
             System.out.println(camion.getId() + "          "+ camion.getMatricula() + "           " + camion.getModelo());
            }
            System.out.println("Seleccione el filtro para modificar camiones:\n\n");
            parametros = imprimeParametros(entradaEscaner);
            
            int actualizados = camionDAO.actualizar(parametros, filtros, session);
            System.out.println("Se actualizaron "+actualizados+" camiones.\n\n");
            
        }else{
            System.out.println("\nNo se encontró información\n");
        }
    }

    private static void eliminarCamionesPorFiltro(Scanner entradaEscaner, Session session) {        
        HashMap<String, Object>  filtros = null;
        System.out.println("Seleccione el filtro para eliminar camiones:\n");
        filtros = imprimeParametros(entradaEscaner);
        
        //Realizar consulta
        CamionDAO camionDAO = new CamionDAO();
        List<Camion> listaCamiones = camionDAO.obtenerRegistrosParametros(filtros, session);
        
        if(listaCamiones != null){
            System.out.println("ID        MATRICULA          MODELO");
            for(Camion camion : listaCamiones){
             System.out.println(camion.getId() + "          "+ camion.getMatricula() + "           " + camion.getModelo());
            }
            System.out.println("Desea eliminar camiones:\n");
            System.out.println("1 - Sí:\n");
            System.out.println("2 - No:\n");
            String confirmacion = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanne
            if(confirmacion != null && confirmacion.trim().equals("1")){                
                int eliminados = camionDAO.eliminar(filtros, session);
                System.out.println("Se eliminaron "+eliminados+" camiones.\n\n");
            }
            
        }else{
            System.out.println("\nNo se encontró información\n");
        }
    }
    
}
