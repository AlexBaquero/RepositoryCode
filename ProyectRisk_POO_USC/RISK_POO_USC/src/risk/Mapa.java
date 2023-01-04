package risk;

import java.util.ArrayList;
import java.util.HashMap;
import risk.exception.ExcepcionComando;
import risk.exception.ExcepcionGeo;

public class Mapa {

    Menu menu;
    
    private int numeroContinentes = 6;
    private ArrayList<Continente> continentes = new ArrayList<>();
    private ArrayList<Pais> paises = new ArrayList<>();
    private ArrayList<Pais> paisesAmericaNorte = new ArrayList<>();
    private ArrayList<Pais> paisesAmericaSur = new ArrayList<>();
    private ArrayList<Pais> paisesEuropa = new ArrayList<>();
    private ArrayList<Pais> paisesAfrica = new ArrayList<>();
    private ArrayList<Pais> paisesAsia = new ArrayList<>();
    private ArrayList<Pais> paisesOceania = new ArrayList<>();
    private ArrayList<ArrayList<Casilla>> tablero = new ArrayList<>();

    private HashMap<String, Pais> paisesHash = new HashMap<String, Pais>();
    private HashMap<String, Continente> continentesHash = new HashMap<String, Continente>();

    private ArrayList<Casilla> Fila1 = new ArrayList<>();
    private ArrayList<Casilla> Fila2 = new ArrayList<>();
    private ArrayList<Casilla> Fila3 = new ArrayList<>();
    private ArrayList<Casilla> Fila4 = new ArrayList<>();
    private ArrayList<Casilla> Fila5 = new ArrayList<>();
    private ArrayList<Casilla> Fila6 = new ArrayList<>();
    private ArrayList<Casilla> Fila7 = new ArrayList<>();
    private ArrayList<Casilla> Fila8 = new ArrayList<>();

    public static ConsolaNormal consola = new ConsolaNormal();

    public Mapa() {
        crearPaisesCasillas();
        crearContinentes();
        imprimirMapa();
    }

    //vamos creando cada pais con sus atributos correspondientes y los vamos añadiendo a su correspondiente
    //hashmap
    public void crearPaisesCasillas() {
        //Paises de America del Norte
        Pais Alaska = new Pais("América Del Norte", "Alaska", "\u001B[45;30m", "   Alaska  ", "Violeta");
        Pais Alberta = new Pais("América Del Norte", "Alberta", "\u001B[45;30m", "  Alberta  ", "Violeta");
        Pais EstadosUnidosDelOeste = new Pais("América Del Norte", "Estados Unidos Del Oeste", "\u001B[45;30m", " USAOeste  ", "Violeta");
        Pais TerritoriosDelNoroeste = new Pais("América Del Norte", "Territorios Del Noroeste", "\u001B[45;30m", " TNoroeste ", "Violeta");
        Pais Ontario = new Pais("América Del Norte", "Ontario", "\u001B[45;30m", "  Ontario  ", "Violeta");
        Pais EstadosUnidosDelEste = new Pais("América Del Norte", "Estados Unidos Del Este", "\u001B[45;30m", "  USAEste  ", "Violeta");
        Pais AmericaCentral = new Pais("América Del Norte", "América Central", "\u001B[45;30m", " AmeCentra ", "Violeta");
        Pais Groenlandia = new Pais("América Del Norte", "Groenlandia", "\u001B[45;30m", " Groenlan  ", "Violeta");
        Pais Quebec = new Pais("América Del Norte", "Quebec", "\u001B[45;30m", "  Quebec   ", "Violeta");
        paisesAmericaNorte.add(Alaska);
        paisesAmericaNorte.add(Alberta);
        paisesAmericaNorte.add(EstadosUnidosDelOeste);
        paisesAmericaNorte.add(TerritoriosDelNoroeste);
        paisesAmericaNorte.add(Ontario);
        paisesAmericaNorte.add(EstadosUnidosDelEste);
        paisesAmericaNorte.add(AmericaCentral);
        paisesAmericaNorte.add(Groenlandia);
        paisesAmericaNorte.add(Quebec);
        //Paises de America del Sur
        Pais Venezuela = new Pais("América Del Sur", "Venezuela", "\033[41;30m", " Venezuela ", "Rojo");
        Pais Peru = new Pais("América Del Sur", "Perú", "\033[41;30m", "    Perú   ", "Rojo");
        Pais Argentina = new Pais("América Del Sur", "Argentina", "\033[41;30m", " Argentina ", "Rojo");
        Pais Brasil = new Pais("América Del Sur", "Brasil", "\033[41;30m", "  Brasil   ", "Rojo");
        paisesAmericaSur.add(Venezuela);
        paisesAmericaSur.add(Peru);
        paisesAmericaSur.add(Argentina);
        paisesAmericaSur.add(Brasil);
        //Pises de Europa
        Pais Islandia = new Pais("Europa", "Islandia", "\u001B[43;30m", " Islandia  ", "Amarillo");
        Pais Escandinavia = new Pais("Europa", "Escandinavia", "\u001B[43;30m", " Escandina ", "Amarillo");
        Pais GranBretaña = new Pais("Europa", "Gran Bretaña", "\u001B[43;30m", " GBretaña  ", "Amarillo");
        Pais EuropaOccidental = new Pais("Europa", "Europa Occidental", "\u001B[43;30m", "  EurOcc   ", "Amarillo");
        Pais EuropaDelNorte = new Pais("Europa", "Europa Del Norte", "\u001B[43;30m", " EurNorte  ", "Amarillo");
        Pais EuropaDelSur = new Pais("Europa", "Europa Del Sur", "\u001B[43;30m", "  EurSur   ", "Amarillo");
        Pais Rusia = new Pais("Europa", "Rusia", "\u001B[43;30m", "   Rusia   ", "Amarillo");
        paisesEuropa.add(Islandia);
        paisesEuropa.add(Escandinavia);
        paisesEuropa.add(GranBretaña);
        paisesEuropa.add(EuropaOccidental);
        paisesEuropa.add(EuropaDelNorte);
        paisesEuropa.add(EuropaDelSur);
        paisesEuropa.add(Rusia);
        //Paises de Africa
        Pais AfricaDelNorte = new Pais("África", "África Del Norte", "\033[42;30m", "  ANorte   ", "Verde");
        Pais Congo = new Pais("África", "Congo", "\033[42;30m", "   Congo   ", "Verde");
        Pais Egipto = new Pais("África", "Egipto", "\033[42;30m", "  Egipto   ", "Verde");
        Pais AfricaOriental = new Pais("África", "África Oriental", "\033[42;30m", " AOriental ", "Verde");
        Pais Sudafrica = new Pais("África", "Sudáfrica", "\033[42;30m", " Sudáfrica ", "Verde");
        Pais Madagascar = new Pais("África", "Madagasca", "\033[42;30m", " Madagasca ", "Verde");
        paisesAfrica.add(AfricaDelNorte);
        paisesAfrica.add(Congo);
        paisesAfrica.add(Egipto);
        paisesAfrica.add(AfricaOriental);
        paisesAfrica.add(Sudafrica);
        paisesAfrica.add(Madagascar);

        //Paises de Asia
        Pais Siberia = new Pais("Asia", "Siberia", "\033[46;30m", "  Siberia  ", "Cyan");
        Pais Yakustsk = new Pais("Asia", "Yakustsk", "\033[46;30m", " Yakustsk  ", "Cyan");
        Pais Urales = new Pais("Asia", "Urales", "\033[46;30m", "  Urales   ", "Cyan");
        Pais Afganistan = new Pais("Asia", "Afganistan", "\033[46;30m", "   Afgan   ", "Cyan");
        Pais OrienteMedio = new Pais("Asia", "Oriente Medio", "\033[46;30m", "  OMedio   ", "Cyan");
        Pais Kamchatka = new Pais("Asia", "Kamchatka", "\033[46;30m", " Kamchatka ", "Cyan");
        Pais Irkutsk = new Pais("Asia", "Irkutsk", "\033[46;30m", "  Irkursk  ", "Cyan");
        Pais Mongolia = new Pais("Asia", "Mongolia", "\033[46;30m", " Mongolia  ", "Cyan");
        Pais China = new Pais("Asia", "China", "\033[46;30m", "   China   ", "Cyan");
        Pais India = new Pais("Asia", "India", "\033[46;30m", "   India   ", "Cyan");
        Pais Japon = new Pais("Asia", "Japón", "\033[46;30m", "   Japón   ", "Cyan");
        Pais SuresteAsiatico = new Pais("Asia", "Sureste Asiático", "\033[46;30m", " SAsiático ", "Cyan");
        paisesAsia.add(Siberia);
        paisesAsia.add(Yakustsk);
        paisesAsia.add(Urales);
        paisesAsia.add(Afganistan);
        paisesAsia.add(OrienteMedio);
        paisesAsia.add(Kamchatka);
        paisesAsia.add(Irkutsk);
        paisesAsia.add(Mongolia);
        paisesAsia.add(China);
        paisesAsia.add(India);
        paisesAsia.add(Japon);
        paisesAsia.add(SuresteAsiatico);

        //Paises de Oceania
        Pais Indonesia = new Pais("Oceanía", "Indonesia", "\033[44;30m", " Indonesia ", "Azul");
        Pais AustraliaOccidental = new Pais("Oceanía", "Australia Occidental", "\033[44;30m", " AusOccid  ", "Azul");
        Pais NuevaGuinea = new Pais("Oceanía", "Nueva Guinea", "\033[44;30m", "  NGuinea  ", "Azul");
        Pais AustraliaOriental = new Pais("Oceanía", "Australia Oriental", "\033[44;30m", " AusOrient ", "Azul");
        paisesOceania.add(Indonesia);
        paisesOceania.add(NuevaGuinea);
        paisesOceania.add(AustraliaOriental);
        paisesOceania.add(AustraliaOccidental);
        //Guardo todos los paises en un arraylist
        paises.addAll(paisesOceania);
        paises.addAll(paisesAmericaNorte);
        paises.addAll(paisesAmericaSur);
        paises.addAll(paisesEuropa);
        paises.addAll(paisesAsia);
        paises.addAll(paisesAfrica);

        //Guardado de los países en un hasmap, utilizando su abreviatura como clave
        paisesHash.put("Alaska", Alaska);
        paisesHash.put("Alberta", Alberta);
        paisesHash.put("USAOeste", EstadosUnidosDelOeste);
        paisesHash.put("TNoroeste", TerritoriosDelNoroeste);
        paisesHash.put("Ontario", Ontario);
        paisesHash.put("USAEste", EstadosUnidosDelEste);
        paisesHash.put("AmeCentra", AmericaCentral);
        paisesHash.put("Groenlan", Groenlandia);
        paisesHash.put("Quebec", Quebec);
        paisesHash.put("Venezuela", Venezuela);
        paisesHash.put("Perú", Peru);
        paisesHash.put("Argentina", Argentina);
        paisesHash.put("Brasil", Brasil);
        paisesHash.put("Islandia", Islandia);
        paisesHash.put("Escandina", Escandinavia);
        paisesHash.put("GBretaña", GranBretaña);
        paisesHash.put("EurOcc", EuropaOccidental);
        paisesHash.put("EurNorte", EuropaDelNorte);
        paisesHash.put("EurSur", EuropaDelSur);
        paisesHash.put("Rusia", Rusia);
        paisesHash.put("ANorte", AfricaDelNorte);
        paisesHash.put("Congo", Congo);
        paisesHash.put("Egipto", Egipto);
        paisesHash.put("AOriental", AfricaOriental);
        paisesHash.put("Sudáfrica", Sudafrica);
        paisesHash.put("Madagasca", Madagascar);
        paisesHash.put("Siberia", Siberia);
        paisesHash.put("Yakustsk", Yakustsk);
        paisesHash.put("Urales", Urales);
        paisesHash.put("Afgan", Afganistan);
        paisesHash.put("OMedio", OrienteMedio);
        paisesHash.put("Kamchatka", Kamchatka);
        paisesHash.put("Irkutsk", Irkutsk);
        paisesHash.put("Mongolia", Mongolia);
        paisesHash.put("China", China);
        paisesHash.put("India", India);
        paisesHash.put("Japón", Japon);
        paisesHash.put("SAsiático", SuresteAsiatico);
        paisesHash.put("Indonesia", Indonesia);
        paisesHash.put("AusOccid", AustraliaOccidental);
        paisesHash.put("NGuinea", NuevaGuinea);
        paisesHash.put("AusOrient", AustraliaOriental);

        //Se añaden las fronteras de cada país
        ArrayList<Pais> FAlaska = new ArrayList<>();
        FAlaska.add(TerritoriosDelNoroeste);
        FAlaska.add(Alberta);
        FAlaska.add(Kamchatka);
        Alaska.setFronteras(FAlaska);

        ArrayList<Pais> FTerritoriosDelNoroeste = new ArrayList<>();
        FTerritoriosDelNoroeste.add(Alaska);
        FTerritoriosDelNoroeste.add(Ontario);
        FTerritoriosDelNoroeste.add(Groenlandia);
        TerritoriosDelNoroeste.setFronteras(FTerritoriosDelNoroeste);

        ArrayList<Pais> FGroenlandia = new ArrayList<>();
        FGroenlandia.add(TerritoriosDelNoroeste);
        FGroenlandia.add(Quebec);
        FGroenlandia.add(Islandia);
        Groenlandia.setFronteras(FGroenlandia);

        ArrayList<Pais> FIslandia = new ArrayList<>();
        FIslandia.add(Groenlandia);
        FIslandia.add(Escandinavia);
        Islandia.setFronteras(FIslandia);

        ArrayList<Pais> FEscandinavia = new ArrayList<>();
        FEscandinavia.add(Islandia);
        FEscandinavia.add(Siberia);
        FEscandinavia.add(GranBretaña);
        Escandinavia.setFronteras(FEscandinavia);

        ArrayList<Pais> FSiberia = new ArrayList<>();
        FSiberia.add(Escandinavia);
        FSiberia.add(Yakustsk);
        FSiberia.add(EuropaDelNorte);
        Siberia.setFronteras(FSiberia);

        ArrayList<Pais> FYakustsk = new ArrayList<>();
        FYakustsk.add(Siberia);
        FYakustsk.add(Rusia);
        FYakustsk.add(Kamchatka);
        Yakustsk.setFronteras(FYakustsk);

        ArrayList<Pais> FKamchatka = new ArrayList<>();
        FKamchatka.add(Yakustsk);
        FKamchatka.add(Irkutsk);
        FKamchatka.add(Alaska);
        Kamchatka.setFronteras(FKamchatka);

        ArrayList<Pais> FAlberta = new ArrayList<>();
        FAlberta.add(Alaska);
        FAlberta.add(EstadosUnidosDelOeste);
        FAlberta.add(Ontario);
        Alberta.setFronteras(FAlberta);

        ArrayList<Pais> FOntario = new ArrayList<>();
        FOntario.add(Alberta);
        FOntario.add(Ontario);
        FOntario.add(EstadosUnidosDelEste);
        FOntario.add(Quebec);
        Ontario.setFronteras(FOntario);

        ArrayList<Pais> FQuebec = new ArrayList<>();
        FQuebec.add(Ontario);
        FQuebec.add(Groenlandia);
        Quebec.setFronteras(FQuebec);

        ArrayList<Pais> FGranBretaña = new ArrayList<>();
        FGranBretaña.add(Escandinavia);
        FGranBretaña.add(EuropaDelNorte);
        FGranBretaña.add(EuropaOccidental);
        GranBretaña.setFronteras(FGranBretaña);

        ArrayList<Pais> FEuropaDelNorte = new ArrayList<>();
        FEuropaDelNorte.add(GranBretaña);
        FEuropaDelNorte.add(EuropaDelSur);
        FEuropaDelNorte.add(Siberia);
        FEuropaDelNorte.add(Rusia);
        EuropaDelNorte.setFronteras(FEuropaDelNorte);

        ArrayList<Pais> FRusia = new ArrayList<>();
        FRusia.add(Urales);
        FRusia.add(EuropaDelNorte);
        FRusia.add(Yakustsk);
        FRusia.add(Irkutsk);
        Rusia.setFronteras(FRusia);

        ArrayList<Pais> FIrkutsk = new ArrayList<>();
        FIrkutsk.add(Rusia);
        FIrkutsk.add(Mongolia);
        FIrkutsk.add(Kamchatka);
        Irkutsk.setFronteras(FIrkutsk);

        ArrayList<Pais> FEstadosUnidosDelOeste = new ArrayList<>();
        FEstadosUnidosDelOeste.add(Alberta);
        FEstadosUnidosDelOeste.add(EstadosUnidosDelEste);
        EstadosUnidosDelOeste.setFronteras(FEstadosUnidosDelOeste);

        ArrayList<Pais> FEstadosUnidosDelEste = new ArrayList<>();
        FEstadosUnidosDelEste.add(EstadosUnidosDelOeste);
        FEstadosUnidosDelEste.add(AmericaCentral);
        FEstadosUnidosDelEste.add(Ontario);
        EstadosUnidosDelEste.setFronteras(FEstadosUnidosDelEste);

        ArrayList<Pais> FEuropaOccidental = new ArrayList<>();
        FEuropaOccidental.add(GranBretaña);
        FEuropaOccidental.add(EuropaDelSur);
        EuropaOccidental.setFronteras(FEuropaOccidental);

        ArrayList<Pais> FEuropaDelSur = new ArrayList<>();
        FEuropaDelSur.add(EuropaOccidental);
        FEuropaDelSur.add(EuropaDelNorte);
        FEuropaDelSur.add(Urales);
        EuropaDelSur.setFronteras(FEuropaDelSur);

        ArrayList<Pais> FUrales = new ArrayList<>();
        FUrales.add(EuropaDelSur);
        FUrales.add(Afganistan);
        FUrales.add(Mongolia);
        FUrales.add(Rusia);
        Urales.setFronteras(FUrales);

        ArrayList<Pais> FMongolia = new ArrayList<>();
        FMongolia.add(Irkutsk);
        FMongolia.add(China);
        FMongolia.add(Urales);
        FMongolia.add(Japon);
        Mongolia.setFronteras(FMongolia);

        ArrayList<Pais> FJapon = new ArrayList<>();
        FJapon.add(Mongolia);
        Japon.setFronteras(FJapon);

        ArrayList<Pais> FAmericaCentral = new ArrayList<>();
        FAmericaCentral.add(Venezuela);
        FAmericaCentral.add(EstadosUnidosDelEste);
        AmericaCentral.setFronteras(FAmericaCentral);

        ArrayList<Pais> FAfganistan = new ArrayList<>();
        FAfganistan.add(OrienteMedio);
        FAfganistan.add(Urales);
        FAfganistan.add(China);
        Afganistan.setFronteras(FAfganistan);

        ArrayList<Pais> FChina = new ArrayList<>();
        FChina.add(Afganistan);
        FChina.add(India);
        FChina.add(Mongolia);
        China.setFronteras(FChina);

        ArrayList<Pais> FVenezuela = new ArrayList<>();
        FVenezuela.add(Peru);
        FVenezuela.add(AmericaCentral);
        Venezuela.setFronteras(FVenezuela);

        ArrayList<Pais> FAfricaDelNorte = new ArrayList<>();
        FAfricaDelNorte.add(Brasil);
        FAfricaDelNorte.add(Congo);
        FAfricaDelNorte.add(EuropaOccidental);
        FAfricaDelNorte.add(Egipto);
        AfricaDelNorte.setFronteras(FAfricaDelNorte);

        ArrayList<Pais> FEgipto = new ArrayList<>();
        FEgipto.add(AfricaDelNorte);
        FEgipto.add(AfricaOriental);
        FEgipto.add(EuropaDelSur);
        FEgipto.add(OrienteMedio);
        Egipto.setFronteras(FEgipto);

        ArrayList<Pais> FOrienteMedio = new ArrayList<>();
        FOrienteMedio.add(Egipto);
        FOrienteMedio.add(Afganistan);
        FOrienteMedio.add(India);
        OrienteMedio.setFronteras(FOrienteMedio);

        ArrayList<Pais> FIndia = new ArrayList<>();
        FIndia.add(OrienteMedio);
        FIndia.add(China);
        FIndia.add(SuresteAsiatico);
        India.setFronteras(FIndia);

        ArrayList<Pais> FSuresteAsiatico = new ArrayList<>();
        FSuresteAsiatico.add(India);
        FSuresteAsiatico.add(Indonesia);
        SuresteAsiatico.setFronteras(FSuresteAsiatico);

        ArrayList<Pais> FPeru = new ArrayList<>();
        FPeru.add(Venezuela);
        FPeru.add(Argentina);
        FPeru.add(Brasil);
        Peru.setFronteras(FPeru);

        ArrayList<Pais> FBrasil = new ArrayList<>();
        FBrasil.add(Peru);
        FBrasil.add(AfricaDelNorte);
        Brasil.setFronteras(FBrasil);

        ArrayList<Pais> FCongo = new ArrayList<>();
        FCongo.add(AfricaDelNorte);
        FCongo.add(AfricaOriental);
        Congo.setFronteras(FCongo);

        ArrayList<Pais> FAfricaOriental = new ArrayList<>();
        FAfricaOriental.add(Egipto);
        FAfricaOriental.add(Congo);
        FAfricaOriental.add(Sudafrica);
        AfricaOriental.setFronteras(FAfricaOriental);

        ArrayList<Pais> FArgentina = new ArrayList<>();
        FArgentina.add(Peru);
        Argentina.setFronteras(FArgentina);

        ArrayList<Pais> FSudafrica = new ArrayList<>();
        FSudafrica.add(AfricaOriental);
        FSudafrica.add(Madagascar);
        Sudafrica.setFronteras(FSudafrica);

        ArrayList<Pais> FMadagascar = new ArrayList<>();
        FMadagascar.add(Sudafrica);
        Madagascar.setFronteras(FMadagascar);

        ArrayList<Pais> FIndonesia = new ArrayList<>();
        FIndonesia.add(AustraliaOccidental);
        FIndonesia.add(NuevaGuinea);
        Indonesia.setFronteras(FIndonesia);

        ArrayList<Pais> FNuevaGuinea = new ArrayList<>();
        FNuevaGuinea.add(Indonesia);
        FNuevaGuinea.add(AustraliaOriental);
        NuevaGuinea.setFronteras(FNuevaGuinea);

        ArrayList<Pais> FAustraliaOccidental = new ArrayList<>();
        FAustraliaOccidental.add(AustraliaOriental);
        FAustraliaOccidental.add(Indonesia);
        AustraliaOccidental.setFronteras(FAustraliaOccidental);

        ArrayList<Pais> FAustraliaOriental = new ArrayList<>();
        FAustraliaOriental.add(AustraliaOccidental);
        FAustraliaOriental.add(NuevaGuinea);
        AustraliaOriental.setFronteras(FAustraliaOriental);

        //CREACION DE LAS CASILLAS
        //FILA 1
        Casilla c1 = Casilla.casillaPais(Alaska, 0, 0);
        Casilla c2 = Casilla.casillaPais(TerritoriosDelNoroeste, 1, 0);
        Casilla c3 = Casilla.casillaPais(Groenlandia, 2, 0);
        Casilla c4 = Casilla.casillaFrontera(3, 0, Casilla.tipoFrontera.HORIZONTAL);
        Casilla c5 = Casilla.casillaPais(Islandia, 4, 0);
        Casilla c6 = Casilla.casillaPais(Escandinavia, 5, 0);
        Casilla c7 = Casilla.casillaPais(Siberia, 6, 0);
        Casilla c8 = Casilla.casillaPais(Yakustsk, 7, 0);
        Casilla c9 = Casilla.casillaPais(Kamchatka, 8, 0);
        Casilla c10 = Casilla.casillaFrontera(9, 0, Casilla.tipoFrontera.HORIZONTAL);
        Casilla c11 = Casilla.casillaFrontera(10, 0, Casilla.tipoFrontera.HORIZONTAL);

        //FILA 2
        Casilla c12 = Casilla.casillaPais(Alberta, 0, 1);
        Casilla c13 = Casilla.casillaPais(Ontario, 1, 1);
        Casilla c14 = Casilla.casillaPais(Quebec, 2, 1);
        Casilla c15 = Casilla.casillaOceano(3, 1);
        Casilla c16 = Casilla.casillaOceano(4, 1);
        Casilla c17 = Casilla.casillaPais(GranBretaña, 5, 1);
        Casilla c18 = Casilla.casillaPais(EuropaDelNorte, 6, 1);
        Casilla c19 = Casilla.casillaPais(Rusia, 7, 1);
        Casilla c20 = Casilla.casillaPais(Irkutsk, 8, 1);
        Casilla c21 = Casilla.casillaOceano(9, 1);
        Casilla c22 = Casilla.casillaOceano(10, 1);

        //FILA 3
        Casilla c23 = Casilla.casillaPais(EstadosUnidosDelOeste, 0, 2);
        Casilla c24 = Casilla.casillaPais(EstadosUnidosDelEste, 1, 2);
        Casilla c25 = Casilla.casillaOceano(2, 2);
        Casilla c26 = Casilla.casillaOceano(3, 2);
        Casilla c27 = Casilla.casillaOceano(4, 2);
        Casilla c28 = Casilla.casillaPais(EuropaOccidental, 5, 2);
        Casilla c29 = Casilla.casillaPais(EuropaDelSur, 6, 2);
        Casilla c30 = Casilla.casillaPais(Urales, 7, 2);
        Casilla c31 = Casilla.casillaPais(Mongolia, 8, 2);
        Casilla c32 = Casilla.casillaPais(Japon, 9, 2);
        Casilla c33 = Casilla.casillaOceano(10, 2);

        //FILA 4
        Casilla c34 = Casilla.casillaOceano(0, 3);
        Casilla c35 = Casilla.casillaPais(AmericaCentral, 1, 2);
        Casilla c36 = Casilla.casillaOceano(2, 3);
        Casilla c37 = Casilla.casillaOceano(3, 3);
        Casilla c38 = Casilla.casillaOceano(4, 3);
        Casilla c39 = Casilla.casillaFrontera(5, 3, Casilla.tipoFrontera.VERTICAL);
        Casilla c40 = Casilla.casillaFrontera(6, 3, Casilla.tipoFrontera.VERTICAL);
        Casilla c41 = Casilla.casillaPais(Afganistan, 7, 3);
        Casilla c42 = Casilla.casillaPais(China, 8, 3);
        Casilla c43 = Casilla.casillaOceano(9, 3);
        Casilla c44 = Casilla.casillaOceano(10, 3);

        //FILA 5
        Casilla c45 = Casilla.casillaOceano(0, 4);
        Casilla c46 = Casilla.casillaPais(Venezuela, 1, 4);
        Casilla c47 = Casilla.casillaOceano(2, 4);
        Casilla c48 = Casilla.casillaOceano(3, 4);
        Casilla c49 = Casilla.casillaFrontera(4, 4, Casilla.tipoFrontera.HORIZONTAL);
        Casilla c50 = Casilla.casillaPais(AfricaDelNorte, 5, 4);
        Casilla c51 = Casilla.casillaPais(Egipto, 6, 4);
        Casilla c52 = Casilla.casillaPais(OrienteMedio, 7, 4);
        Casilla c53 = Casilla.casillaPais(India, 8, 4);
        Casilla c54 = Casilla.casillaPais(SuresteAsiatico, 9, 4);
        Casilla c55 = Casilla.casillaOceano(10, 4);

        //FILA 6
        Casilla c56 = Casilla.casillaOceano(0, 5);
        Casilla c57 = Casilla.casillaPais(Peru, 1, 5);
        Casilla c58 = Casilla.casillaPais(Brasil, 2, 5);
        Casilla c59 = Casilla.casillaFrontera(3, 5, Casilla.tipoFrontera.HORIZONTAL);
        Casilla c60 = Casilla.casillaOceano(4, 5);
        Casilla c61 = Casilla.casillaPais(Congo, 5, 5);
        Casilla c62 = Casilla.casillaPais(AfricaOriental, 6, 5);
        Casilla c63 = Casilla.casillaOceano(7, 5);
        Casilla c64 = Casilla.casillaOceano(8, 5);
        Casilla c65 = Casilla.casillaFrontera(9, 5, Casilla.tipoFrontera.VERTICAL);
        Casilla c66 = Casilla.casillaOceano(10, 5);

        //FILA 7
        Casilla c67 = Casilla.casillaOceano(0, 6);
        Casilla c68 = Casilla.casillaPais(Argentina, 1, 6);
        Casilla c69 = Casilla.casillaOceano(2, 6);
        Casilla c70 = Casilla.casillaOceano(3, 6);
        Casilla c71 = Casilla.casillaOceano(4, 6);
        Casilla c72 = Casilla.casillaOceano(5, 6);
        Casilla c73 = Casilla.casillaPais(Sudafrica, 6, 6);
        Casilla c74 = Casilla.casillaPais(Madagascar, 7, 6);
        Casilla c75 = Casilla.casillaOceano(8, 6);
        Casilla c76 = Casilla.casillaPais(Indonesia, 9, 6);
        Casilla c77 = Casilla.casillaPais(NuevaGuinea, 10, 6);

        //FILA 8
        Casilla c78 = Casilla.casillaOceano(0, 6);
        Casilla c79 = Casilla.casillaOceano(1, 6);
        Casilla c80 = Casilla.casillaOceano(2, 6);
        Casilla c81 = Casilla.casillaOceano(3, 6);
        Casilla c82 = Casilla.casillaOceano(4, 6);
        Casilla c83 = Casilla.casillaOceano(5, 6);
        Casilla c84 = Casilla.casillaOceano(6, 6);
        Casilla c85 = Casilla.casillaOceano(7, 6);
        Casilla c86 = Casilla.casillaOceano(8, 6);
        Casilla c87 = Casilla.casillaPais(AustraliaOccidental, 9, 6);
        Casilla c88 = Casilla.casillaPais(AustraliaOriental, 10, 6);

        Fila1.add(c1);
        Fila1.add(c2);
        Fila1.add(c3);
        Fila1.add(c4);
        Fila1.add(c5);
        Fila1.add(c6);
        Fila1.add(c7);
        Fila1.add(c8);
        Fila1.add(c9);
        Fila1.add(c10);
        Fila1.add(c11);

        Fila2.add(c12);
        Fila2.add(c13);
        Fila2.add(c14);
        Fila2.add(c15);
        Fila2.add(c16);
        Fila2.add(c17);
        Fila2.add(c18);
        Fila2.add(c19);
        Fila2.add(c20);
        Fila2.add(c21);
        Fila2.add(c22);

        Fila3.add(c23);
        Fila3.add(c24);
        Fila3.add(c25);
        Fila3.add(c26);
        Fila3.add(c27);
        Fila3.add(c28);
        Fila3.add(c29);
        Fila3.add(c30);
        Fila3.add(c31);
        Fila3.add(c32);
        Fila3.add(c33);

        Fila4.add(c34);
        Fila4.add(c35);
        Fila4.add(c36);
        Fila4.add(c37);
        Fila4.add(c38);
        Fila4.add(c39);
        Fila4.add(c40);
        Fila4.add(c41);
        Fila4.add(c42);
        Fila4.add(c43);
        Fila4.add(c44);

        Fila5.add(c45);
        Fila5.add(c46);
        Fila5.add(c47);
        Fila5.add(c48);
        Fila5.add(c49);
        Fila5.add(c50);
        Fila5.add(c51);
        Fila5.add(c52);
        Fila5.add(c53);
        Fila5.add(c54);
        Fila5.add(c55);

        Fila6.add(c56);
        Fila6.add(c57);
        Fila6.add(c58);
        Fila6.add(c59);
        Fila6.add(c60);
        Fila6.add(c61);
        Fila6.add(c62);
        Fila6.add(c63);
        Fila6.add(c64);
        Fila6.add(c65);
        Fila6.add(c66);

        Fila7.add(c67);
        Fila7.add(c68);
        Fila7.add(c69);
        Fila7.add(c70);
        Fila7.add(c71);
        Fila7.add(c72);
        Fila7.add(c73);
        Fila7.add(c74);
        Fila7.add(c75);
        Fila7.add(c76);
        Fila7.add(c77);

        Fila8.add(c78);
        Fila8.add(c79);
        Fila8.add(c80);
        Fila8.add(c81);
        Fila8.add(c82);
        Fila8.add(c83);
        Fila8.add(c84);
        Fila8.add(c85);
        Fila8.add(c86);
        Fila8.add(c87);
        Fila8.add(c88);

        tablero.add(Fila1);
        tablero.add(Fila2);
        tablero.add(Fila3);
        tablero.add(Fila4);
        tablero.add(Fila5);
        tablero.add(Fila6);
        tablero.add(Fila7);
        tablero.add(Fila8);
    }

    //devuelve el pais perteneciente al hashmap
    public HashMap<String, Pais> getPaises() {
        return this.paisesHash;
    }

    //devuelve el contiente perteneciente al hashmap
    public HashMap<String, Continente> getContinentes() {
        return this.continentesHash;
    }

    //Vamos creando los continentes con sus correspondientes atributos y los vamos añadiendo al hasshmap
    public void crearContinentes() {
        Continente asia = new Continente(paisesAsia, "Asia", "Cyan", "Asia");
        Continente africa = new Continente(paisesAfrica, "África", "Verde", "África");
        Continente americaDelNorte = new Continente(paisesAmericaNorte, "América Del Norte", "Violeta", "AméricaNorte");
        Continente americaDelSur = new Continente(paisesAmericaSur, "América Del Sur", "Rojo", "AméricaSur");
        Continente europa = new Continente(paisesEuropa, "Europa", "Amarillo", "Europa");
        Continente oceania = new Continente(paisesOceania, "Oceanía", "Azul", "Oceanía");

        //Guardo todos los continentes en un arraylist
        continentes.add(asia);
        continentes.add(africa);
        continentes.add(americaDelNorte);
        continentes.add(americaDelSur);
        continentes.add(europa);
        continentes.add(oceania);
        //Guardo todos los continentes en un HashMap donde su abreviatura es la clave
        continentesHash.put("Asia", asia);
        continentesHash.put("Africa", africa);
        continentesHash.put("AmericaNorte", americaDelNorte);
        continentesHash.put("AmericaSur", americaDelSur);
        continentesHash.put("Europa", europa);
        continentesHash.put("Oceania", oceania);

        continentesHash.put("Asia", asia);
        continentesHash.put("África", africa);
        continentesHash.put("América Del Norte", americaDelNorte);
        continentesHash.put("América Del Sur", americaDelSur);
        continentesHash.put("Europa", europa);
        continentesHash.put("Oceanía", oceania);
    }

    //devolvemos el continente del pais
    public Continente continente_pais(Pais p) {
        String nombre = p.getContinente();
        Continente c = continentesHash.get(nombre);
        return c;
    }

    //imprimimos el tablero del mapa
    public void imprimirMapa() {
        
        if (!paises.isEmpty()) {
            for (int i = 0; i < 8; i++) {
                String mensaje = "|===========|===========|===========|===========|===========|===========|===========|===========|===========|===========|===========|\n";
                consola.imprimir(mensaje);
                
                for (int j = 0; j < 11; j++) {
                    String mensaje2 = "|" + tablero.get(i).get(j).toString();
                    consola.imprimir(mensaje2);
                    ;
                }
                String mensaje3 = "|" + "\n";
                consola.imprimir(mensaje3);
                
            }
            String mensaje4 = "|===========|===========|===========|===========|===========|===========|===========|===========|===========|===========|===========|\n";
            consola.imprimir(mensaje4);
            
        } else {
            String mensaje5 = "No hay paises";
            consola.imprimir(mensaje5);
            
        }
    }

    //imprimimos el tablero del juego con los ejercitos correspondientes
    public void verMapa() throws ExcepcionComando {
        String comando = "$> VER MAPA";
        if (paises.isEmpty()) {
            menu.error(comando,99,ListadoErrores.getError(99).getDescripcionError());
            throw new ExcepcionComando(99, ListadoErrores.getError(99).getDescripcionError());
            
            
        } else {
            for (int i = 0; i < 8; i++) {
                String mensaje1 = "|===========|===========|===========|===========|===========|===========|===========|===========|===========|===========|===========|\n";
                consola.imprimir(mensaje1);
                
                for (int j = 0; j < 11; j++) {
                    String mensaje2 = "|" + tablero.get(i).get(j).toString();
                    consola.imprimir(mensaje2);
                    
                }
                String mensaje3 = "|" + "\n";
                consola.imprimir(mensaje3);
                
                for (int j = 0; j < 11; j++) {
                    if (tablero.get(i).get(j).esPais()) {
                        String mensaje4 = "| " + tablero.get(i).get(j).color() + tablero.get(i).get(j).ejercitosCasilla() + "\u001B[0m" + "         ";
                        consola.imprimir(mensaje4);
                        
                    } else {
                        String mensaje5 = "|           ";
                        consola.imprimir(mensaje5);
                        
                    }
                }
                String mensaje6 = "|" + "\n";
                consola.imprimir(mensaje6);
                
            }
            String mensaje7 = "|===========|===========|===========|===========|===========|===========|===========|===========|===========|===========|===========|\n";
            consola.imprimir(mensaje7);
        }
    }

    //devolvemos el continente que corresponde a la abreviatura del pais
    void obtenerContinente(String abreviaturaP) throws ExcepcionGeo {
        String comando = "$> OBTENER CONTINENTE " + abreviaturaP;
        String c = paisesHash.get(abreviaturaP).getContinente();
        if (c == null) {
            menu.error(comando,109,ListadoErrores.getError(109).getDescripcionError());
            throw new ExcepcionGeo(109, ListadoErrores.getError(109).getDescripcionError());
        }
        String mensaje = "{" + "\ncontinente: " + paisesHash.get(abreviaturaP).getContinente() + "\n}\n";
        consola.imprimir(mensaje);
         EscrituraFichero.escribirResultado(comando, mensaje, false);
    }

    //devolvemos el color correspondiente con la abreviatura del pais introducida
    void obtenerColor(String abreviaturaP) throws ExcepcionGeo {
        String comando = "$> OBTENER COLOR " + abreviaturaP;
        Pais n = paisesHash.get(abreviaturaP);
        if (n == null) {
            menu.error(comando,109,ListadoErrores.getError(109).getDescripcionError());
            throw new ExcepcionGeo(109, ListadoErrores.getError(109).getDescripcionError());
        }
        String c = n.getNombreColor();
        if (c == null) {
            menu.error(comando,100,ListadoErrores.getError(100).getDescripcionError());
            throw new ExcepcionGeo(100, ListadoErrores.getError(100).getDescripcionError());
        }

        String mensaje = "{" + "\ncolor: " + n.getNombreColor() + "\n}\n";
        consola.imprimir(mensaje);
         EscrituraFichero.escribirResultado(comando, mensaje, false);
    }

    //devolvemos el pais correspondiente con la abreviatura del continente introducida
    void obtenerPaises(String abreviaturaC) throws ExcepcionGeo {
        String comando = "$> OBTENER  PAISES " + abreviaturaC;
        Continente c = continentesHash.get(abreviaturaC);
        String p = c.getPaises().toString(); //Me esta dando errores al hacer un getPaises
        if (c == null) {
            menu.error(comando,102,ListadoErrores.getError(102).getDescripcionError());
            throw new ExcepcionGeo(102, ListadoErrores.getError(102).getDescripcionError());
        }

        String mensaje = "{" + "\npaises: " + p + "\n}\n";
        consola.imprimir(mensaje);
         EscrituraFichero.escribirResultado(comando, mensaje, false);
    }

    //devolvemos la frontera correspondiente a la abreviatura del pais introducida
    void obtenerFrontera(String abreviaturaP) throws ExcepcionGeo {
         String comando = "$> OBTENER FRONTERA" + abreviaturaP;
        String f = paisesHash.get(abreviaturaP).getFronteras().toString();
        if (f == null) {
            menu.error(comando,107,ListadoErrores.getError(107).getDescripcionError());
            throw new ExcepcionGeo(107, ListadoErrores.getError(107).getDescripcionError());
        }

        String mensaje = "{" + "\nfrontera: " + f + "\n}\n";
        consola.imprimir(mensaje);
         EscrituraFichero.escribirResultado(comando, mensaje, false);
    }

    //Comprobamos si dos pais son frontera o no
    public boolean esFrontera(String abreviatura_pais1, String abreviatura_pais2) {
        Pais p1 = paisesHash.get(abreviatura_pais1);
        Pais p2 = paisesHash.get(abreviatura_pais2);

        if (p1.getFronteras().contains(p2)) {
            return true;
        } else {
            return false;
        }
    }

}
