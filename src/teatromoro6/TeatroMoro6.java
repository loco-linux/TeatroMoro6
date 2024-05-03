package teatromoro6;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;


public class TeatroMoro6 {

    // Variables estáticas
    // defino colores para optimizar visualizacion de texto del programa   
    public static String rojo       ="\033[31m"; 
    public static String verde      ="\033[32m"; 
    public static String azul       ="\033[34m"; 
    public static String celeste    ="\033[36m"; 
    public static String reset      ="\u001B[0m";   
    
    public static String nombreTeatro = "TEATRO MORO";  
    public static String ID="";
    
    public static int totalVendidas = 0;
    
    public static boolean llenoZonaA = false;
    public static boolean llenoZonaB = false;
    public static boolean llenoZonaC = false;
    public static boolean llenoZonaD = false;
    
    // Abstraccion de representacion de datos
     // Declaro una matriz unidimensional para las zonas de entradas
    public static String[] tipoEntrada = {"A", "B", "C", "D"};
     // Declaro matriz unidimensional para cuantificar entradas por sector
    public static int[] contadorEntrada = {0,0,0,0};
 
    public static int indicadorEnt = 0; 
     // declaro matriz 4x5
    // el numero de asientos es fijo, por lo que un Array es la mejor opcion para representarlos
     public static int[][] ubicacionAsiento = {
         {0,0,0,0,0}, // [0,0][0,1][0,2][0,3][0,4]
         {0,0,0,0,0}, // [1,0][1,1][1,2][1,3][1,4]
         {0,0,0,0,0}, // [2,0][2,1][2,2][2,3][2,4]
         {0,0,0,0,0}, // [3,0][3,1][3,2][3,3][3,4]
     };
    
    // Crea una nueva Lista tipo ArrayList para almacenar cadenas
    static List<String>     clienteDetalle      =new ArrayList<>();
    static List<String>     IDcliente           =new ArrayList<>();
    static List<String>     ubicacion           =new ArrayList<>();
    static List<Integer>    costoUnitario       =new ArrayList<>();
    static List<Double>     descuentoAplicado   =new ArrayList<>();
    static List<Double>     costoFinal          =new ArrayList<>();
    

    public static void main(String[] args) {
        // Metodo que imprime una bienvenida en pantalla
        bienvenida(); 
        
        // Metodo que solicita informacion del cliente
        detalleCliente();
        
        // Metodo que muestra plano del teatro
        planoTeatro();
        
        // Variables de input de usuario desde teclado
        Scanner teclado = new Scanner(System.in);
        
        // Definicion de variables locales
        boolean encontrado = false, continua = false; 
        int opcion=0;
             
        while(encontrado==false){
                
        do{ 
        try{
            continua = false;
            System.out.println(celeste+"[MENU]"+reset);
            System.out.println("Presione 1 si quiere "+verde+"[Cambio Cliente]"+reset);
            System.out.println("Presione 2 si quiere "+verde+"[Plano Teatro]"+reset);
            System.out.println("Presione 3 si quiere "+verde+"[Comprar Entrada]"+reset);
            System.out.println("Presione 4 si quiere "+verde+"[Modificar Entrada]"+reset);
            System.out.println("Presione 5 si quiere "+verde+"[Eliminar Entrada]"+reset);
            System.out.println("Presione 6 si quiere "+verde+"[Boleta]"+reset);
            System.out.println("Presione 7 si quiere "+verde+"[Ingresos totales]"+reset);
            System.out.println("Presione 8 si quiere "+verde+"[Salir]"+reset);
            System.out.print("--->Ingrese opcion: ");
        
        opcion = teclado.nextInt();
                   
        switch(opcion){
            case 1 -> detalleCliente();
            case 2 -> planoTeatro();
            case 3 -> {
                // Ingreso un numero aleatorio para la ID
                int random = (int)Math.floor(Math.random()*1000+1);
                IDcliente.add(ID+random);
                comprarEntrada();
                }
            case 4 -> modificarEntrada();
            case 5 -> eliminarEntrada();
            case 6 -> generadorBoleta();
            case 7 -> ingresosTotales();
            case 8 -> {
                encontrado = true; // para salir del bucle while
                System.out.println("");
                System.out.println(celeste+"---Gracias por su visita al Teatro Moro---"+reset);
                System.out.println("");
                }
            // control de errores
            default -> System.out.println(rojo+"[ERROR] Ingrese una opcion valida!"+reset); 
            } // fin switch
        
        // control de errores por error de ingreso de datos por parte del usuario
            } catch(InputMismatchException ex){
           System.out.println(rojo+"[ERROR] Ingrese un numero entero"+reset);
           teclado.next();
           continua = true;
         } // fin TryCatch
        }while(continua);
     }
  
      teclado.close();
        
    }
    
        
    public static void generadorBoleta(){
        
        if (ubicacion.isEmpty()){
            System.out.println(rojo+"[ERROR] Aun no se han comprado entradas!"+reset);
        }else{
        int tamaño = ubicacion.size();
        for(int i=0; i < tamaño; i++){ 
        System.out.println("""
                           -----------------------------------------
                           ************   TEATRO MORO   ************
                           -----------------------------------------""");
        System.out.println(">>>ID venta: " + IDcliente.get(i));
        System.out.println("*Ubicacion: " + ubicacion.get(i) );
        System.out.println("*Costo Base: $" + costoUnitario.get(i) );
        
        boolean dcto10 = descuentoAplicado.get(i) == 0.1;
        boolean dcto15 = descuentoAplicado.get(i) == 0.15;
        
        if(dcto10){
                System.out.println("*Descuento Aplicado: 10%");
            } else if(dcto15){
                System.out.println("*Descuento Aplicado: 15%");
            } else {
                System.out.println("*Descuento Aplicado: 0%");
            }
        
        System.out.println("*Costo Final: $" + costoFinal.get(i) );
        System.out.println("""
                           -----------------------------------------
                             Gracias por su visita al Teatro Moro
                           -----------------------------------------
                           
                           
                           """);
        }
        }  
    } // fin metodo generadorBoleta()
    
    
    public static void bienvenida(){
        
    // Despliegue Bienvenida al sistema
        System.out.println(celeste+"*******************************");
        System.out.println(celeste+"********* "+rojo+nombreTeatro+celeste+" *********");              
        System.out.println(celeste+"*******************************"); 
        System.out.println(rojo+"------ TICKETERA VIRTUAL ------"+reset); 
        System.out.println("");
               
    }
    
    
    
    public static void detalleCliente(){
        // Metodo que solicita datos del cliente para asignarle un ID a sus compras
        Scanner datos = new Scanner(System.in);
        String nombre, apellido, nombreCompleto, inicialN, inicialA;
       
        System.out.print("Ingrese su nombre: ");
        nombre = datos.nextLine();
        System.out.print("Ingrese su apellido: ");
        apellido = datos.nextLine();
        
        nombreCompleto = nombre.toUpperCase() + " " + apellido.toUpperCase();
        clienteDetalle.add(nombreCompleto);
        
        inicialN = nombre.toUpperCase().substring(0,1);
        inicialA = apellido.toUpperCase().substring(0,1);
        
        ID = inicialN + inicialA;
        int client = clienteDetalle.size();
        
        System.out.println(">>Su nombre es " + clienteDetalle.get(client-1));
        System.out.println(">>Su ID de venta es: " + ID);
 
    }
    
    
    public static void eliminarEntrada(){
        
        Scanner datos = new Scanner(System.in);
        String buscar;
        boolean encontrado = false;
        int contador = 0;
        
        if(IDcliente.isEmpty()){
            System.out.println(rojo+"[ERROR] Aun no se compran entradas..."+reset);
        }else{
        System.out.print("Ingrese ID venta: ");
        buscar = datos.nextLine();
     
        int size = IDcliente.size();
        for(int i=0; i< size; i++){
           if(IDcliente.get(i).equals(buscar)){
                IDcliente.remove(contador);
                eliminarAsiento(contador);
                ubicacion.remove(contador);
                costoUnitario.remove(contador);
                descuentoAplicado.remove(contador);
                costoFinal.remove(contador);
                totalVendidas--;
                System.out.println(buscar+rojo+"-->[ELIMINADO]"+reset);
                encontrado = true;
                break;
           }
           contador++;
        } 
       
        if(encontrado == false){
           System.out.println(rojo+"[ERROR] ID venta no encontrado!"+reset);
        }   
        }
    } // fin metodo eliminarEntrada()
    
    
    public static void modificarEntrada(){

        Scanner datos = new Scanner(System.in);
        String buscar;
        boolean encontrado = false;
        int contador = 0, indice = IDcliente.size();
        
        if(IDcliente.isEmpty()){
            System.out.println(rojo+"[ERROR] Aun no se compran entradas..."+reset);
        }else{
        System.out.print("Ingrese ID venta: ");
        buscar = datos.nextLine();
        
        // funcion para comprar entrada
        
        for(int i=0; i<indice ; i++){
            if(IDcliente.get(i).equals(buscar)){
                encontrado = true;
                break;
            }
            contador++;
            } // fin for, ya con el contador asignado
        
        if (encontrado==false){
            System.out.println(rojo+"[ERROR] No se encontro ID..."+reset);
        }else{
            eliminarAsiento(contador);
            totalVendidas--;
            modificar(contador);
 
            System.out.println(IDcliente.get(contador)+rojo+"-->[MODIFICADO]"+reset);
           }
      datos.reset();  
    }
    } // fin metodo modificarEntrada()
    
    
    public static void modificar(int contador){ 
        // Variables de input de usuario desde teclado
        Scanner teclado2 = new Scanner(System.in);
     
         // Variables locales
        String opcionZona;
        int edad, opcionColumna;
        boolean encontra2 = false, bucleEdad = false;  
     
      do{
        
        System.out.print("Ingresa la Zona deseada "+verde+"(A, B, C, D)"+reset+": ");          
        opcionZona = teclado2.nextLine().toUpperCase();
              
         switch (opcionZona) {
             case "A" -> {
                 if(llenoZonaA == false){
                 ubicacion.set(contador,"A");
                 costoUnitario.set(contador,30000);
                 encontra2 = true; 
                 Entrada.entradaA++;  
                 totalVendidas++;
                  }else{
                     System.out.println(rojo+"[ERROR] Sin cupos, ingrese una ubicacion en otra Zona."+reset);
                    }
                }
             case "B" -> {   
                 if(llenoZonaB == false){
                 ubicacion.set(contador,"B");
                 costoUnitario.set(contador,20000);
                 encontra2 = true;
                 Entrada.entradaB++;
                 totalVendidas++;
                 }else{
                     System.out.println(rojo+"[ERROR] Sin cupos, ingrese una ubicacion en otra Zona."+reset);
                    }
                }  
             case "C" -> {  
                 if(llenoZonaC == false){
                 ubicacion.set(contador,"C");
                 costoUnitario.set(contador,15000);
                 encontra2 = true;
                 Entrada.entradaC++;
                 totalVendidas++;
                 }else{
                     System.out.println(rojo+"[ERROR] Sin cupos, ingrese una ubicacion en otra Zona."+reset);
                    }
                 }
             case "D" -> {  
                 if(llenoZonaD == false){
                 ubicacion.set(contador,"D");
                 costoUnitario.set(contador,10000);
                 encontra2 = true;
                 Entrada.entradaD++;
                 totalVendidas++;
                }else{
                     System.out.println(rojo+"[ERROR] Sin cupos, ingrese una ubicacion en otra Zona."+reset);
                    }
             }
             default -> System.out.println(rojo+"[ERROR] Entrada no valida. Reintente..."+reset);
             } // fin switch
        }while(encontra2 == false);
        
      
     do{
         encontra2=false;
                      
         do{
             opcionColumna = 0;
            
                System.out.print("Ingrese la Columna deseada " +verde+"(1-5): "+reset);
                String opColumna = teclado2.nextLine(); 
                // Otra manera de manejar errores de ingreso de datos
                switch(opColumna.trim()){
                    case "1", "2", "3", "4", "5" -> opcionColumna = Integer.parseInt(opColumna.trim());
                    default -> System.out.println(rojo+"[ERROR] Ingrese un numero entre 1 al 5..."+reset);
                }
         }while(opcionColumna<1 || opcionColumna>5);
         
                      
         // Asignacion de Asiento
         encontra2 = asignarAsiento(opcionZona, opcionColumna);
         if(encontra2==true){
             ubicacion.set(contador,opcionZona+opcionColumna);
         }

     }while(encontra2 == false);
  
      teclado2.reset();
      
      // Descuentos por Estudiante / Adulto Mayor
     while(bucleEdad == false){

     do{
        edad = 0;
        System.out.print("Presione "+rojo+"1"+reset+" si es [E]studiante o "+rojo+"2"+reset+" si es [T]ercera edad\nde lo contrario, presione "+rojo+"3"+reset+":  ");
        String ed = teclado2.nextLine();
        switch(ed.trim()){
                    case "1", "2", "3" -> edad = Integer.parseInt(ed.trim());
                    default -> System.out.println(rojo+"[ERROR] Ingrese un numero entre 1 al 3..."+reset);
                } 
        }while(edad<1 || edad >3);

        // Aplica descuentos
        switch(edad){
            case 1 -> {
                descuentoAplicado.set(contador, 0.10);
                bucleEdad = true;
                break;
                    } 
            case 2 -> {
                descuentoAplicado.set(contador, 0.15);
                bucleEdad = true;
                break;
                    }
            default -> {
                descuentoAplicado.set(contador, 0.0);
                bucleEdad = true;
                break;
                    }           
            }// fin switch
     }

        // Calculo para definir el costo final, y lo agrega a la Lista definida para ello
        int costo = costoUnitario.get(contador);
        double dcto = descuentoAplicado.get(contador);
        double costoF = costo - (costo * dcto);
        costoFinal.set(contador,costoF);
        
    } // fin metodo modificar()
    
    
    public static boolean asignarAsiento(String fila, int columna){
        
                  // Usa el asiento y lo registra en el array
         for(int i = 0; i<4; i++){
            for (int j=0; j<5; j++){
                if(
                        tipoEntrada[i].equals(fila) && 
                        ubicacionAsiento[i][columna-1]!=1
                    ){
                ubicacionAsiento[i][columna-1] = 1;
                contadorEntrada[i]++;
                indicadorEnt++;
                System.out.println("Su ubicacion escogida: " + rojo + fila + columna + reset);
                
                if(contadorEntrada[i]==5){
                    switch(fila){
                        case "A" -> llenoZonaA = true;
                        case "B" -> llenoZonaB = true;
                        case "C" -> llenoZonaC = true;
                        case "D" -> llenoZonaD = true;
                        }  
                    } 
                    return true;
                } else if(
                        tipoEntrada[i].equals(fila) && 
                        ubicacionAsiento[i][columna-1]==1
                        ){
                    System.out.println(rojo+"[ERROR] Ubicacion ocupada. Seleccione otra..."+reset);
                    break;
                }
                
                } // for2
                } // for1   
        
       return false; 
    }
    
    
    public static void eliminarAsiento(int indice){
        
        String asiento = ubicacion.get(indice);
        String fila = asiento.substring(0,1);
        String columna = asiento.substring(1,2);
        // cambio de tipo String a int
        int col = Integer.parseInt(columna);
         
        switch(fila){
            case "A" -> {
                ubicacionAsiento[0][col-1]=0;
                Entrada.entradaA--;
                }
            case "B" -> {
            ubicacionAsiento[1][col-1]=0;
            Entrada.entradaB--;
                }
            case "C" -> {
                ubicacionAsiento[2][col-1]=0;
                Entrada.entradaC--;
                }
            case "D" -> {
                ubicacionAsiento[3][col-1]=0;
                Entrada.entradaD--;
                }
        } 
    }
    
    
    public static void comprarEntrada(){
        // Variables de input de usuario desde teclado
     Scanner teclado2 = new Scanner(System.in);
     
     // Variables locales
     String opcionZona;
     int edad;
     boolean encontra2 = false, bucleEdad = false;
     int opcionColumna;
    
     
      do{
        
        System.out.print("Ingresa la Zona deseada "+verde+"(A, B, C, D)"+reset+": ");          
        opcionZona = teclado2.nextLine().toUpperCase();
              
         switch (opcionZona) {
             case "A" -> {
                 if(llenoZonaA == false){
                 ubicacion.add("A");
                 costoUnitario.add(30000);
                 encontra2 = true; 
                 Entrada.entradaA++;  
                 totalVendidas++;
                  }else{
                     System.out.println(rojo+"[ERROR] Sin cupos, ingrese una ubicacion en otra Zona."+reset);
                    }
                }
             case "B" -> {   
                 if(llenoZonaB == false){
                 ubicacion.add("B");
                 costoUnitario.add(20000);
                 encontra2 = true;
                 Entrada.entradaB++;
                 totalVendidas++;
                 }else{
                     System.out.println(rojo+"[ERROR] Sin cupos, ingrese una ubicacion en otra Zona."+reset);
                    }
                }  
             case "C" -> {  
                 if(llenoZonaC == false){
                 ubicacion.add("C");
                 costoUnitario.add(15000);
                 encontra2 = true;
                 Entrada.entradaC++;
                 totalVendidas++;
                 }else{
                     System.out.println(rojo+"[ERROR] Sin cupos, ingrese una ubicacion en otra Zona."+reset);
                    }
                 }
             case "D" -> {  
                 if(llenoZonaD == false){
                 ubicacion.add("D");
                 costoUnitario.add(10000);
                 encontra2 = true;
                 Entrada.entradaD++;
                 totalVendidas++;
                }else{
                     System.out.println(rojo+"[ERROR] Sin cupos, ingrese una ubicacion en otra Zona."+reset);
                    }
             }
             default -> System.out.println(rojo+"[ERROR] Entrada no valida. Reintente..."+reset);
             } // fin switch
        }while(encontra2 == false);
        
      
     do{
         encontra2=false;
         do{
         opcionColumna = 0;    
         System.out.print("Ingrese la Columna deseada " +verde+"(1-5): "+reset);
        // opcionColumna = teclado2.nextInt();
         String opColumna = teclado2.nextLine(); 
         // Otra manera de manejar errores de ingreso de datos
         switch(opColumna.trim()){
            case "1", "2", "3", "4", "5" -> opcionColumna = Integer.parseInt(opColumna.trim());
            default -> System.out.println(rojo+"[ERROR] Ingrese un numero entre 1 al 5..."+reset);
            }
         
         }while(opcionColumna<1 || opcionColumna>5);
         
         // Asignacion de Asiento
         encontra2 = asignarAsiento(opcionZona, opcionColumna);

         if(encontra2==true){
            ubicacion.set(ubicacion.size()-1, opcionZona + opcionColumna);
            }
         
     }while(encontra2 == false);
  
      teclado2.reset();
      
      // Descuentos por Estudiante / Adulto Mayor
     while(bucleEdad == false){

     do{
        edad = 0;
        System.out.print("Presione "+rojo+"1"+reset+" si es [E]studiante o "+rojo+"2"+reset+" si es [T]ercera edad\nde lo contrario, presione "+rojo+"3"+reset+":  ");
        String ed = teclado2.nextLine();
        switch(ed.trim()){
            case "1", "2", "3" -> edad = Integer.parseInt(ed.trim());
            default -> System.out.println(rojo+"[ERROR] Ingrese un numero entre 1 al 3..."+reset);
            }  
        }while(edad<1 || edad >3);

        // Aplica descuentos
        switch(edad){
            case 1 -> {
                descuentoAplicado.add(0.10);
                bucleEdad = true;
                break;
                    } 
            case 2 -> {
                descuentoAplicado.add(0.15);
                bucleEdad = true;
                break;
                    }
            default -> {
                descuentoAplicado.add(0.0);
                bucleEdad = true;
                break;
                    }           
            }// fin switch
     }
     
     // Calculo para definir el costo final, y lo agrega a la Lista definida para ello
     int indice = ubicacion.size();
     int costo = costoUnitario.get(indice-1);
     double dcto = descuentoAplicado.get(indice-1);
     double costoF = costo - (costo * dcto);
     costoFinal.add(costoF);
     
     System.out.println(">ID venta: " + IDcliente.get(indice-1));
    } // fin metodo comprarEntrada()
    
    
    public static void ingresosTotales(){
        
        Entrada.entradasVendidas();
        
        double costo = 0.0;
        // Si la lista no esta vacia, usa un FOR para sumar el ingreso total
        if(!ubicacion.isEmpty()){
            int size = ubicacion.size();
            for(int i=0; i < size; i++){
                costo += costoFinal.get(i);
            }
  
        System.out.println(rojo + ">> Ingresos Totales acumulados: " + azul + costo + reset);
        
        if(totalVendidas >= 5){
            costo = costo - (costo * 0.1);
            System.out.println(rojo+">>> Total con descuento: " + costo + reset);
            }
        
        } // fin if
    } // fin metodo ingresosTotales() 
    
    public static void planoTeatro(){
         
        System.out.println("\n"+celeste+"***** PLANO DEL TEATRO *****"+reset);
        for(int a=0; a<10; a++){
            System.out.print(" ");
        }
        System.out.println(verde+"ESCENARIO   "+reset);
        for(int i = 0; i<4; i++){
            System.out.print("Zona " + tipoEntrada[i] + " ");
            for (int j=0; j<5; j++){
                System.out.print("[");
                if(ubicacionAsiento[i][j]==1){
                    System.out.print(rojo+ubicacionAsiento[i][j]+reset);
                }else{
                    System.out.print(ubicacionAsiento[i][j]);
                }
                System.out.print("]");
                }
             System.out.println("");
            }
        System.out.println(celeste+"****************************"+reset);
        int resto = 20 - totalVendidas;
        System.out.println("");
        System.out.println(         "[0] Disponible       -> "+resto);
        System.out.println(rojo+    "[1] No disponible    -> "+totalVendidas+reset);
        System.out.println();
          
        // Si se venden todas las entradas, imprime un mensaje de Sold Out
        if(resto==0){
            System.out.println(rojo+"### SOLD OUT ###"+reset);
        }
     }
    
    
} // Fin clase TeatroMoro6


class Entrada {
    
    // Variables de instancia (global)
    static int entradaA = 0;
    static int entradaB = 0;
    static int entradaC = 0;
    static int entradaD = 0;
    static int cantidadEntradasVendidas = 0;
     
    public static void entradasVendidas(){
        boolean hayEntrada = false;
        
        if(entradaA != 0){
            System.out.println(TeatroMoro6.rojo+"[A]:      " + TeatroMoro6.reset + entradaA);
            hayEntrada = true;
        } 
        
        if (entradaB != 0){
            System.out.println(TeatroMoro6.rojo+"[B]:      " + TeatroMoro6.reset + entradaB);
            hayEntrada = true;
        } 
        
        if (entradaC != 0){
            System.out.println(TeatroMoro6.rojo+"[C]:      " + TeatroMoro6.reset + entradaC);
            hayEntrada = true;
        }
        
        if (entradaD != 0){
            System.out.println(TeatroMoro6.rojo+"[D]:      " + TeatroMoro6.reset + entradaD);
            hayEntrada = true;
        }
        
        if(hayEntrada == false){
            System.out.println(TeatroMoro6.rojo+"[ERROR] Aun no compra entradas!"+TeatroMoro6.reset);
        }
        
        cantidadEntradasVendidas = entradaA + entradaB + entradaC + entradaD;
        
        if(cantidadEntradasVendidas>0){
            System.out.println(TeatroMoro6.azul+"[TOTAL]:  " + cantidadEntradasVendidas +TeatroMoro6.reset);
        }
        
    } // Fin metodo entradasVendidas()
    
} // Fin clase Entrada