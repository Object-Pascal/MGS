package PresentationTier.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.example.gaanplaatsstaan.MapsActivity;
import com.example.gaanplaatsstaan.R;

import java.util.ArrayList;

import DataTier.Database.DatabaseManager;
import LogicTier.RouteManager.Route.Route;
import LogicTier.RouteManager.Route.RouteHelper;
import LogicTier.RouteManager.Route.Waypoint;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        DatabaseManager databaseManager = new DatabaseManager(this);

        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "VVV", "51.588762", "4.776913", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "De Bruine Pij", "51.588791", "4.775477", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Corenmaet", "51.589308", "4.774484", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "OMearas Irish Pub", "51.589448", "4.775846", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Kasteel van Breda", "51.590504", "4.776221", 0, null));
        databaseManager.insertWaypointIntoDB(new Waypoint(false, false, "Café Publieke Werken", "51.588973", "4.778062", 0, null));*/

        ArrayList<Waypoint> wp = new ArrayList<Waypoint>();
        ArrayList<String> images = new ArrayList<>();
        images.add("https://vvvbreda.nl/content/uploads/2019/03/IMG_20190328_121002.jpg");
        wp.add(new Waypoint(false, false, "VVV", "Het Begin en het Eindpunt van deze route. U kan veel informatie opvragen bij de VVV over breda en zijn oudheid.", "51.588762", "4.776913", 0, images));
        images = new ArrayList<>();
        images.add("https://media-cdn.tripadvisor.com/media/photo-s/15/a4/74/91/buiten-kant.jpg");
        wp.add(new Waypoint(false, false, "De Bruine Pij", "Dat ene café in Breda, onder de Grote Kerk. Kom in de zomer lekker in de zon zitten op ons mooie terras aan de Grote Kerk, of zoek in de winter de gezelligheid op in ons knusse bruine café. Geniet het hele jaar door van lekkere koffie, huisgemaakte appeltaart en de mooiste speciaalbieren.Sinds 1975", "51.588791", "4.775477", 0, images));
        images = new ArrayList<>();
        images.add("https://corenmaet.nl/wp-content/media/buiten.jpg");
        wp.add(new Waypoint(false, false, "Corenmaet", "Zoals u wellicht ziet is dit café helemaal vernieuwd gelukkig is het oude gevoel er nog steeds. Daarnaast zitten we natuurlijk nog steeds op de Havermarkt. HET gezelligste plein met de meeste zon in Breda. In de zomer lekker de hele dag buiten genieten, of tijdens de koude perioden juist heerlijk knus naar buiten kijken onder het genot van een speciaalbiertje of een van onze wijnen.", "51.589308", "4.774484", 0, images));
        images = new ArrayList<>();
        images.add("https://indebuurt.nl/breda/wp-content/uploads/2018/03/omaeras-e1520954240271.jpg");
        wp.add(new Waypoint(false, false, "O'Mearas Irish Pub", "O'Mearas is het oudste Ierse pub van Breda, waar je diverse Ierse bieren en whiskey’s kunt drinken en waar je kunt altijd live sports kijken zoals eredivisie , Engelse/Schots premiership, Rugby (6 Nations/WK enz) en zoveel meer , ook regelmatig kunt genieten van  live muziek", "51.589448", "4.775846", 0, images));
        images = new ArrayList<>();
        images.add("http://johnooms.nl/wp-content/uploads/2017/06/Kasteel-van-Breda1.jpg");
        wp.add(new Waypoint(false, false, "Kasteel van Breda", "Het Kasteel van Breda herbergt sinds 1828 de Koninklijke Militaire Academie en is ingericht als kazerne bestemd voor de opleiding van officieren van de Nederlandse krijgsmacht. Het kasteel is militair terrein van Defensie en niet vrij toegankelijk. Rondleidingen over het kasteel onder leiding van ervaren gidsen worden verzorgd door de VVV-Breda", "51.590504", "4.776221", 0, images));
        images = new ArrayList<>();
        images.add("https://vvvbreda.nl/content/uploads/2018/08/Valkenbergpark-Breda-1-origineel-foto-Mari%C3%ABlle-Houben-1-e1563801409597-1120x604.jpg");
        wp.add(new Waypoint(false, false, "Stadspark Valkenberg", "Dit fraaie stadspark verbindt het station van Breda met de binnenstad. Tot 1812 deed het Valkenberg dienst als kasteeltuin voor de Heren van Breda. Het dankt zijn naam aan een valkenhuis dat aan de rand stond en van waaruit de kasteelbewoners en hun gasten de valkenjacht bedreven.", "51.590613", "4.777537", 0, images));
        images = new ArrayList<>();
        images.add("https://indebuurt.nl/breda/wp-content/uploads/2017/08/publieke-werken-st-annastraat-breda-2.jpg");
        wp.add(new Waypoint(false, false, "Café Publieke Werken", "Al 22 jaar is Café Publieke Werken een begrip in Breda. De ongedwongen sfeer, oprechte gastvrijheid en het lekker eten & drinken maken ‘PW’ tot een café waar iedereen zich thuis voelt. Na de verbouwing in augustus 2015 staan er maar liefst 15 bieren op tap en is er nog veel meer keuze op fles! Wanneer kom jij deze proeven?", "51.588973", "4.778062", 0, images));

        ArrayList<Waypoint> wp1 = new ArrayList<Waypoint>();
        ArrayList<String> images1 = new ArrayList<>();
        images1.add("https://vvvbreda.nl/content/uploads/2019/03/IMG_20190328_121002.jpg");
        wp1.add(new Waypoint(false, false, "VVV", "Het Begin en het Eindpunt van deze route. U kan veel informatie opvragen bij de VVV over breda en zijn oudheid.", "51.588762", "4.776913", 0, images1));
        images1 = new ArrayList<>();
        images1.add("https://media-cdn.tripadvisor.com/media/photo-s/15/a4/74/91/buiten-kant.jpg");
        wp1.add(new Waypoint(false, false, "De Bruine Pij", "Dat ene café in Breda, onder de Grote Kerk. Kom in de zomer lekker in de zon zitten op ons mooie terras aan de Grote Kerk, of zoek in de winter de gezelligheid op in ons knusse bruine café. Geniet het hele jaar door van lekkere koffie, huisgemaakte appeltaart en de mooiste speciaalbieren.Sinds 1975", "51.5874764", "4.77672994", 0, images1));
        images1 = new ArrayList<>();
        images1.add("https://corenmaet.nl/wp-content/media/buiten.jpg");
        wp1.add(new Waypoint(false, false, "Corenmaet", "Zoals u wellicht ziet is dit café helemaal vernieuwd gelukkig is het oude gevoel er nog steeds. Daarnaast zitten we natuurlijk nog steeds op de Havermarkt. HET gezelligste plein met de meeste zon in Breda. In de zomer lekker de hele dag buiten genieten, of tijdens de koude perioden juist heerlijk knus naar buiten kijken onder het genot van een speciaalbiertje of een van onze wijnen.", "51.58702978", "4.77585018", 0, images1));
        images1 = new ArrayList<>();
        images1.add("https://indebuurt.nl/breda/wp-content/uploads/2018/03/omaeras-e1520954240271.jpg");
        wp1.add(new Waypoint(false, false, "O'Mearas Irish Pub", "O'Mearas is het oudste Ierse pub van Breda, waar je diverse Ierse bieren en whiskey’s kunt drinken en waar je kunt altijd live sports kijken zoals eredivisie , Engelse/Schots premiership, Rugby (6 Nations/WK enz) en zoveel meer , ook regelmatig kunt genieten van  live muziek", "51.58723642", "4.77333963", 0, images1));
        images1 = new ArrayList<>();
        images1.add("http://johnooms.nl/wp-content/uploads/2017/06/Kasteel-van-Breda1.jpg");
        wp1.add(new Waypoint(false, false, "Kasteel van Breda", "Het Kasteel van Breda herbergt sinds 1828 de Koninklijke Militaire Academie en is ingericht als kazerne bestemd voor de opleiding van officieren van de Nederlandse krijgsmacht. Het kasteel is militair terrein van Defensie en niet vrij toegankelijk. Rondleidingen over het kasteel onder leiding van ervaren gidsen worden verzorgd door de VVV-Breda", "51.588103", "4.77297485", 0, images1));
        images1 = new ArrayList<>();
        images1.add("https://vvvbreda.nl/content/uploads/2018/08/Valkenbergpark-Breda-1-origineel-foto-Mari%C3%ABlle-Houben-1-e1563801409597-1120x604.jpg");
        wp1.add(new Waypoint(false, false, "Stadspark Valkenberg", "Dit fraaie stadspark verbindt het station van Breda met de binnenstad. Tot 1812 deed het Valkenberg dienst als kasteeltuin voor de Heren van Breda. Het dankt zijn naam aan een valkenhuis dat aan de rand stond en van waaruit de kasteelbewoners en hun gasten de valkenjacht bedreven.", "51.58926953", "4.77449834", 0, images1));
        images1 = new ArrayList<>();
        images1.add("https://indebuurt.nl/breda/wp-content/uploads/2017/08/publieke-werken-st-annastraat-breda-2.jpg");
        wp1.add(new Waypoint(false, false, "Café Publieke Werken", "Al 22 jaar is Café Publieke Werken een begrip in Breda. De ongedwongen sfeer, oprechte gastvrijheid en het lekker eten & drinken maken ‘PW’ tot een café waar iedereen zich thuis voelt. Na de verbouwing in augustus 2015 staan er maar liefst 15 bieren op tap en is er nog veel meer keuze op fles! Wanneer kom jij deze proeven?", "51.588183", "4.77530301", 0, images1));

        RouteHelper routeHelper = new RouteHelper();
        routeHelper.uploadRoute(this, wp, "Route1");
        routeHelper.uploadRoute(this, wp1, "Route2");

        Route route = routeHelper.getRoute(this, "Route1");
        ArrayList<String> routeNames = routeHelper.getAllRouteNames(this);

        Route route1 = routeHelper.getRoute(this,"Route2");


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(mapsIntent);
                    finish();
                }
            },SPLASH_TIME_OUT );

        }
    }