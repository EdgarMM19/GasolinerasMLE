import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Estat {
    private static final int max_quilometres = 640;
    private static final int max_num_viatges = 5;
    private static final int valor_diposit = 1000;
    private static final int cost_quilometre = 2;
    private static int num_centres;
    private static CentrosDistribucion centres;
    private static int num_gasolineres;
    private static Gasolineras gasolineres;
    // La matriu 'distancies' guarda la distància de Manhattan precalculada entre dos elements de l'àrea geogràfica.
    // Els centres s'indexen amb els elements 0..(num_centres - 1).
    // Les gasolineres s'indexen amb els elements num_centres..(num_centres + num_gasolineres - 1).
    private static int[][] distancies;
    // El vector 'rutes' guarda, per cada centre de distribució, quina ruta té assignada el seu camió cisterna.
    private Ruta[] rutes;
    // La matriu 'assignacio_diposit' guarda, per cada gasolinera, un vector que conté l'índex del centre de distribució
    // assignat a cadascun dels seus dipòsits. Si un dipòsit no té centre de distribució assignat, hi ha -1 a la seva
    // posició.
    private EstatGasolinera[] estat_gasolineres;

    public Estat(@NotNull CentrosDistribucion _centres, @NotNull Gasolineras _gasolineres) {
        num_centres = _centres.size();
        centres = _centres;
        num_gasolineres = _gasolineres.size();
        gasolineres = _gasolineres;

        // Precalculem totes les distàncies per evitar càlculs innecessaris.
        distancies = new int[num_centres + num_gasolineres][num_centres + num_gasolineres];
        for (int i = 0; i < num_centres; ++i) {
            for (int j = 0; j < num_centres; ++j) {
                distancies[i][j] = Utils.GetDistancia(centres.get(i), centres.get(j));
                distancies[j][i] = distancies[i][j];
            }
            for (int j = 0; j < num_gasolineres; ++j) {
                distancies[i][num_centres + j] = Utils.GetDistancia(centres.get(i), gasolineres.get(j));
                distancies[num_centres + j][i] = distancies[i][num_centres + j];
            }
        }

        for (int i = 0; i < num_gasolineres; ++i) {
            for (int j = 0; j < num_centres; ++j) {
                distancies[num_centres + i][j] = Utils.GetDistancia(gasolineres.get(i), centres.get(j));
                distancies[j][num_centres + i] = distancies[num_centres + i][j];
            }
            for (int j = 0; j < num_gasolineres; ++j) {
                distancies[num_centres + i][num_centres + j] = Utils.GetDistancia(gasolineres.get(i),
                        gasolineres.get(j));
                distancies[num_centres + j][num_centres + i]  = distancies[num_centres + i][num_centres + j];
            }
        }

        estat_gasolineres = new EstatGasolinera[num_gasolineres];
        // Omplim l'assignació de dipòsits amb els valors i mides inicials.
        for (int i = 0; i < num_gasolineres; ++i) {
            estat_gasolineres[i] = new EstatGasolinera(gasolineres.get(i));
        }

        // Per cada centre de distribució, li assignem la ruta inicial, que correspon a que el camió cisterna no s'ha
        // mogut del centre.
        rutes = new Ruta[num_centres];
        for (int i = 0; i < num_centres; ++i) {
            rutes[i] = new Ruta(Coordinates.GetCoordsCentre(centres.get(i)), i);
        }
    }

    public Estat(@NotNull Estat antic)
    {
        this.rutes = new Ruta[antic.rutes.length];
        for (int i = 0; i < antic.rutes.length; ++i) {
            this.rutes[i] = new Ruta(antic.rutes[i]);
        }
        this.estat_gasolineres = new EstatGasolinera[antic.estat_gasolineres.length];
        for (int i = 0; i < antic.estat_gasolineres.length; ++i) {
            this.estat_gasolineres[i] = new EstatGasolinera(antic.estat_gasolineres[i]);
        }
    }
    static public int GetDistanciaEntreCentreIGasolinera(int c, int g) {
        return distancies[c][num_centres + g];
    }

    static public int GetDistanciaEntreCentres(int c1, int c2) {
        return distancies[c1][c2];
    }

    static public int GetDistanciaEntreGasolineres(int g1, int g2) {
        return distancies[num_centres + g1][num_centres + g2];
    }

    static public int GetDistanciaEntreNodeIGasolinera(int n, int g) { return distancies[n][num_centres+g]; }

    static public int GetDistanciaEntreNodeICentre(int n, int c) { return distancies[n][c]; }

    public Ruta[] GetRutes()
    {
        return rutes;
    }

    public int AvaluaFuncioHeuristicaBeneficis(){
        int cost_quilometres_totals = 0;
        int diners_cobrats = 0;
        int diners_perduts = 0;

        for (int i = 0; i < num_centres; ++i) {
            cost_quilometres_totals += rutes[i].GetQuilometresRecorreguts() * cost_quilometre;
        }

        for (int i = 0; i < num_gasolineres; ++i) {
            Gasolinera g = gasolineres.get(i);
            ArrayList<Integer> p = g.getPeticiones();
            int num_diposits = p.size();
            for (int j = 0; j < num_diposits; ++j) {
                if (estat_gasolineres[i].GetAssignacioDiposit()[j] != -1) {
                    int percentatge;
                    if (p.get(j) == 0) {
                        percentatge = 102;
                    } else {
                        percentatge = 100 - (1 << p.get(j));
                    }
                    diners_cobrats += valor_diposit * percentatge;
                } else {
                    // percentatge_perdut = (100 - 2^d) - (100 - 2^(d+1)) = 2^d
                    int percentatge_perdut;
                    if (p.get(j) == 0) {
                        percentatge_perdut = 4;
                    } else {
                        percentatge_perdut = 1 << p.get(j);
                    }
                    diners_perduts += valor_diposit * percentatge_perdut;
                }
            }
        }
        // La funcio heuristica s'ha de minimitzar. Canviem el signe.
        return -(diners_cobrats - cost_quilometres_totals - diners_perduts);
    }

    public void CreaEstatInicialBuit() {
        // Com mostra el nom l'estat inicial és aquell on no hi ha res assignat.
    }

    private int GetGasolineraDisponibleMesPropera(int index) {
        int gasolinera_mes_propera = -1;
        for (int i = 0; i < num_gasolineres; ++i) {
            if (!estat_gasolineres[i].GasolineraSatisfeta() &&
                    (gasolinera_mes_propera == -1 ||
                            GetDistanciaEntreNodeIGasolinera(index,i) < GetDistanciaEntreNodeIGasolinera(index,gasolinera_mes_propera))) {
                gasolinera_mes_propera = i;
            }
        }
        return gasolinera_mes_propera;
    }

    public Boolean EsPotAfegirViatge(Ruta ruta, Coordinates coordinates)
    {
        if (!ruta.EsPotAfegirParadaSenseTornarAlCentreDeDistribucio()) {
            ruta.AfegeixParadaAlCentreDeDistribucio();
        }
        if (ruta.GetLastCoordinates() == coordinates && ruta.GetNumViatges() == max_num_viatges) {
            return false;
        }
        return ruta.QuilometresViatgeITornadaAlCentre(coordinates) <= max_quilometres;
    }

    private int GetCentreDisponibleMesProper(int index) {
        int centre_mes_proper = -1;
        for (int i = 0; i < num_centres; ++i) {
            if (EsPotAfegirViatge(rutes[i], Coordinates.GetCoordsGasolinera(gasolineres.get(index))) &&
                    (centre_mes_proper == -1 ||
                            GetDistanciaEntreNodeICentre(num_centres+index,i) < GetDistanciaEntreNodeICentre(num_centres+index, centre_mes_proper))) {
                centre_mes_proper = i;
            }
        }
        return centre_mes_proper;
    }

    public void CreaEstatPropers() {
        // Ens basarem en assignar sempre el camió que està més proper a una gasolinera en cada moment.
        // Per això tenim una cua on fiquem la informació sobre: camió, distància a la gasolinera on es vol dirigir,
        // nombre de viatge que estaria fent.

        // Ens guardem la gasolinera amb alguna ordre no servida més propera a cada punt.
        int [] gasolinera_mes_propera = new int[num_centres+num_gasolineres];
        for (int i = 0; i < num_centres+num_gasolineres; ++i) {
            gasolinera_mes_propera[i] = GetGasolineraDisponibleMesPropera(i);
        }
        class Tuple {
            final int camio;
            final int distancia;
            final int num_parades;
            // Entre 0..(num_centres + num_gasolineres - 1)
            final int index_posicio_actual;
            Tuple (int camio, int distancia, int num_parades, int index_posicio_actual)
            {
                this.camio = camio;
                this.distancia = distancia;
                this.num_parades = num_parades;
                this.index_posicio_actual = index_posicio_actual;
            }
        }
        class TupleComparer implements Comparator<Tuple> {
            public int compare(Tuple t1, Tuple t2) {
                if (t1.distancia < t2.distancia)
                    return 1;
                if (t1.distancia > t2.distancia)
                    return -1;
                if (t1.num_parades < t2.num_parades)
                    return 1;
                if (t1.num_parades > t2.num_parades)
                    return -1;
                return Integer.compare(t1.camio, t2.camio);
            }
        }
        PriorityQueue<Tuple> pq = new PriorityQueue<>(new TupleComparer());
        for (int i = 0; i < num_centres; ++i) {
            pq.add(new Tuple(i, gasolinera_mes_propera[i], 0, i));
        }
        while (!pq.isEmpty()) {
            Tuple act = pq.remove();
            if (rutes[act.camio].GetNumParades() != act.num_parades)
            {
                continue;
            }
            int gasolinera = gasolinera_mes_propera[act.index_posicio_actual];
            if (estat_gasolineres[gasolinera].GasolineraSatisfeta()) {
                gasolinera_mes_propera[act.index_posicio_actual] = GetGasolineraDisponibleMesPropera(act.index_posicio_actual);
                int gasolinera_propera = gasolinera_mes_propera[act.index_posicio_actual];
                if (gasolinera_propera == -1) {
                    continue;
                }
                if (rutes[act.camio].QuilometresViatgeITornadaAlCentre(Coordinates.GetCoordsGasolinera(gasolineres.get(gasolinera_propera))) <= max_quilometres)
                {
                    pq.add(new Tuple(act.camio, GetDistanciaEntreNodeIGasolinera(act.index_posicio_actual, gasolinera_propera), act.num_parades, act.index_posicio_actual));
                }
                else
                {
                    rutes[act.camio].AfegeixParada(Coordinates.GetCoordsCentre(centres.get(act.camio)), act.camio);
                }
            }
            else
            {
                rutes[act.camio].AfegeixParada(Coordinates.GetCoordsGasolinera(gasolineres.get(gasolinera)), num_centres+gasolinera);
                estat_gasolineres[gasolinera].CamioArribat(act.camio);
                int propera_posicio;
                if (!rutes[act.camio].EsPotAfegirParadaSenseTornarAlCentreDeDistribucio()) {
                    rutes[act.camio].AfegeixParada(Coordinates.GetCoordsCentre(centres.get(act.camio)), act.camio);
                    propera_posicio = act.camio;
                    if (rutes[act.camio].GetNumViatges() == max_num_viatges)
                    {
                        continue;
                    }
                }
                else
                {
                    propera_posicio = num_centres+gasolinera;
                }
                int gasolinera_propera = GetGasolineraDisponibleMesPropera(propera_posicio);
                if (gasolinera_propera == -1) {
                    continue;
                }
                if (rutes[act.camio].QuilometresViatgeITornadaAlCentre(Coordinates.GetCoordsGasolinera(gasolineres.get(gasolinera_propera))) <= max_quilometres)
                {
                    pq.add(new Tuple(act.camio, GetDistanciaEntreNodeIGasolinera(propera_posicio, gasolinera_propera), rutes[act.camio].GetNumParades(), propera_posicio));
                }
                else {
                    rutes[act.camio].AfegeixParada(Coordinates.GetCoordsCentre(centres.get(act.camio)), act.camio);
                }
            }
        }
    }

    public void BorraRutaCentre(int index)
    {
        while(rutes[index].GetNumParades() != 0) {
            BorraUltimViatgeCentre(index);
        }
    }

    public void BorraUltimViatgeCentre(int index)
    {
        int gasolinera = rutes[index].GetId(rutes[index].GetNumParades());
        if (gasolinera >= num_centres) {
            estat_gasolineres[gasolinera-num_centres].CamioMarxat(index);
        }
        rutes[index].EliminaParada();
    }

    public void EmplenaCentre(int index)
    {
        while(AfegeixViatgeCentre(index));
    }

    public Boolean AfegeixViatgeCentre(int index)
    {
        if (!rutes[index].EsPotAfegirParadaSenseTornarAlCentreDeDistribucio())
        {
            rutes[index].AfegeixParada(Coordinates.GetCoordsCentre(centres.get(index)), index);
        }
        if (rutes[index].GetLastId() == index && rutes[index].GetNumViatges() == max_num_viatges)
            return false;
        int gasolinera = GetGasolineraDisponibleMesPropera(rutes[index].GetLastId());
        if (gasolinera == -1) return false;
        Coordinates coords_gasolinera = Coordinates.GetCoordsGasolinera(gasolineres.get(gasolinera));
        if (rutes[index].QuilometresViatgeITornadaAlCentre(coords_gasolinera) <= max_quilometres) {
            rutes[index].AfegeixParada(coords_gasolinera, num_centres+gasolinera);
            estat_gasolineres[gasolinera].CamioArribat(index);
            return true;
        }
        return false;
    }

    public Boolean AfegeixViatgeGasolinera(int index)
    {
        if (!estat_gasolineres[index].GasolineraSatisfeta()) {
            int index_centre = GetCentreDisponibleMesProper(index);
            if (index_centre != -1) {
                Coordinates coordinates_gasolinera = Coordinates.GetCoordsGasolinera(gasolineres.get(index));
                if (EsPotAfegirViatge(rutes[index_centre], coordinates_gasolinera)) {
                    rutes[index_centre].AfegeixParada(coordinates_gasolinera, num_centres + index);
                    estat_gasolineres[index].CamioArribat(index_centre);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Estat> SuccessorsParellaDeCamions()
    {
        ArrayList<Estat> sol = new ArrayList<>();
        for (int i = 0; i < num_centres; ++i) {
            for (int j = 0; j < num_centres; ++j) {
                if (i == j) continue;
                Estat nou_estat = new Estat(this);
                nou_estat.BorraRutaCentre(i);
                nou_estat.BorraRutaCentre(j);
                nou_estat.EmplenaCentre(i);
                nou_estat.EmplenaCentre(j);
                //for (int k = 0; k < num_centres; ++k) {
                //    BorraUltimViatgeCentre(k);
                //}
                //for (int k = 0; k < num_gasolineres; ++k) {
                //    AfegeixViatgeGasolinera(k);
                //}
                sol.add(nou_estat);
            }
        }
        return sol;
    }
    public ArrayList<Estat> GetSuccessors() {
        System.out.println("Getting successors " + AvaluaFuncioHeuristicaBeneficis());
        return SuccessorsParellaDeCamions();
    }
}
