package risk;

import java.util.HashMap;

public class ListadoErrores {
    private static final HashMap<Integer,Error> ErroresHash = new HashMap<Integer,Error>();
    
    static{
    
        //vamos definiendo cada error, y luego lo añadimos a un HashMap
        Error e99 = new Error(99,"Comando no permitido en este momento");
        Error e100 = new Error(100,"Color no permitido");
        Error e101 = new Error(101,"Comando incorrecto");
        Error e102 = new Error(102,"El continente no existe");
        Error e103 = new Error(103,"El jugador no existe");
        Error e104 = new Error(104,"El jugador ya existe");
        Error e105 = new Error(105,"Los jugadores no están creados");
        Error e106 = new Error(106,"El mapa no está creado");
        Error e107 = new Error(107,"El mapa ya ha sido creado");
        Error e109 = new Error(109,"El país no existe");
        Error e110 = new Error(110,"El país no pertenece al jugador");
        Error e111 = new Error(111,"El país pertenece al jugador");
        Error e112 = new Error(112,"Los países no son frontera");
        Error e113 = new Error(113,"El país ya está asignado");
        Error e114 = new Error(114,"El color ya está asignado");
        Error e115 = new Error(115,"La misión ya está asignada");
        Error e116 = new Error(116,"La misión no existe");
        Error e117 = new Error(117,"El jugador ya tiene asignada una misión");
        Error e118 = new Error(118,"Las misiones no están asignadas");
        Error e119 = new Error(119,"Ejércitos no disponibles");
        Error e120 = new Error(120,"No hay cartas suficientes");
        Error e121 = new Error(121,"No hay configuración de cambio");
        Error e122 = new Error(122,"Algunas cartas no pertenecen al jugador");
        Error e123 = new Error(123,"Algunas cartas no existen");
        Error e124 = new Error(124,"No hay ejércitos suficientes");
        Error e125 = new Error(125,"El identificador no sigue el formato correcto");
        Error e126 = new Error(126,"Carta de equipamiento ya asignada");
        
        ErroresHash.put(99, e99);
        ErroresHash.put(100, e100);
        ErroresHash.put(101, e101);
        ErroresHash.put(102, e102);
        ErroresHash.put(103, e103);
        ErroresHash.put(104, e104);
        ErroresHash.put(105, e105);
        ErroresHash.put(106, e106);
        ErroresHash.put(107, e107);
        ErroresHash.put(109, e109);
        ErroresHash.put(110, e110);
        ErroresHash.put(111, e111);
        ErroresHash.put(112, e112);
        ErroresHash.put(113, e113);
        ErroresHash.put(114, e114);
        ErroresHash.put(115, e115);
        ErroresHash.put(116, e116);
        ErroresHash.put(117, e117);
        ErroresHash.put(118, e118);
        ErroresHash.put(119, e119);
        ErroresHash.put(120, e120);
        ErroresHash.put(121, e121);
        ErroresHash.put(122, e122);
        ErroresHash.put(123, e123);
        ErroresHash.put(124, e124);
        ErroresHash.put(125, e125);
        ErroresHash.put(126, e126);
        
    }
    
    //devuelve el error correspondiente
    public static Error getError(Integer n){
        return ErroresHash.get(n);
    }

}