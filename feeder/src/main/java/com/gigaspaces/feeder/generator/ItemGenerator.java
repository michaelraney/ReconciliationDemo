package com.gigaspaces.feeder.generator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.gigaspaces.common.model.Item;

public class ItemGenerator {

	private static List<Item> items = generateItems();;
	
	public static Item getRandomItem(ThreadLocalRandom random){
		return items.get(random.nextInt(0, items.size()-1));
	}
	
	public static Item[] getAllItems(){
		return (Item[])items.toArray();
	}
	
	private static List<Item> generateItems(){
		return Arrays.asList(new Item("Bank of America","BAC", new Long(17)),
			new Item("Yamana Gold","AUY", new Long(4)),
			new Item("Kinross Gold","KGC", new Long(2)),
			new Item("Nokia ADS","NOK", new Long(8)),
			new Item("Barrick Gold","ABX", new Long(11)),
			new Item("Crown Castle International","CCI", new Long(77)),
		    new Item("Bank of America","BAC", new Long(17)),
			new Item("Yamana Gold","AUY", new Long(4)),
			new Item("Kinross Gold","KGC", new Long(2)),
			new Item("Nokia ADS","NOK", new Long(8)),
			new Item("Barrick Gold","ABX", new Long(11)),
			new Item("Crown Castle International","CCI", new Long(77)),
			new Item("Citigroup","C", new Long(53)),
			new Item("Ford Motor","F", new Long(14)),
			new Item("General Electric","GE", new Long(25)),
			new Item("Petroleo Brasileiro ADS","PBR", new Long(11)),
			new Item("Twitter","TWTR", new Long(41)),
			new Item("Newmont Mining","NEM", new Long(18)),
			new Item("Goldcorp","GG", new Long(18)),
			new Item("Ambev ADS","ABEV", new Long(6)),
			new Item("Avon Products","AVP", new Long(10)),
			new Item("Pfizer","PFE", new Long(30)),
			new Item("Barclays ADS","BCS", new Long(15)),
			new Item("Alibaba Group Holding ADS","BABA",new Long(98)),
			new Item("Sprint","S", new Long(5)),
			new Item("AT&T","T", new Long(34)),
			new Item("Bristol-Myers Squibb","BMY", new Long(57)),
			new Item("Verizon Communications","VZ", new Long(50)),
			new Item("Alcatel-Lucent ADS","ALU", new Long(3)),
			new Item("Vale ADS","VALE",new Long(9)),
			new Item("Taiwan Semiconductor Manufacturing ADS","TSM", new Long(21)),
			new Item("Rite Aid","RAD", new Long(5)),
			new Item("Boston Scientific","BSX", new Long(13)),
			new Item("Hecla Mining","HL", new Long(2)),
			new Item("Wells Fargo","WFC", new Long(52)),
			new Item("Delta Air Lines","DAL", new Long(40)),
			new Item("Itau Unibanco Holding ADS","ITUB", new Long(14)),
			new Item("KeyCorp","KEY", new Long(13)),
			new Item("JPMorgan Chase","JPM", new Long(60)),
			new Item("Advanced Micro Devices","AMD", new Long(2)),
			new Item("Hewlett-Packard","HPQ", new Long(35)),
			new Item("Freeport-McMoRan","FCX", new Long(28)),
			new Item("General Motors","GM", new Long(31)),
			new Item("AbbVie","ABBV", new Long(63)),
			new Item("IAMGOLD","IAG", new Long(1)),
			new Item("Western Union","WU", new Long(17)),
			new Item("Denbury Resources","DNR", new Long(12)),
			new Item("J.C. Penney","JCP", new Long(7)),
			new Item("Alcoa","AA", new Long(16)),
			new Item("Regions Financial","RF",new Long(9)),
			new Item("Coca-Cola","KO", new Long(41)),
			new Item("Juniper Networks","JNPR", new Long(20)),
			new Item("Exxon Mobil","XOM",new Long(95)),
			new Item("Petroleo Brasileiro ADS A","PBRA", new Long(11)),
			new Item("Banco Santander ADS","SAN", new Long(8)),
			new Item("MasterCard Cl A","MA", new Long(84)),
			new Item("MGM Resorts International","MGM", new Long(23)),
			new Item("Silver Wheaton","SLW", new Long(17)),
			new Item("Banco Santander Brasil ADS","BSBR", new Long(5)),
			new Item("United States Steel","X", new Long(40)),
			new Item("Oracle","ORCL", new Long(38)),
			new Item("Kinder Morgan","KMI", new Long(38)),
			new Item("Halliburton","HAL", new Long(54)),
			new Item("Alpha Natural Resources","ANR", new Long(1)),
			new Item("Eldorado Gold","EGO", new Long(5)),
			new Item("Spansion Cl A","CODE", new Long(19)),
			new Item("Gold Fields ADS","GFI", new Long(3)),
			new Item("BP ADS","BP", new Long(43)),
			new Item("EMC","EMC", new Long(28)),
			new Item("Transocean","RIG", new Long(29)),
			new Item("Merck&Co","MRK", new Long(57)),
			new Item("Dow Chemical","DOW", new Long(49)),
			new Item("Morgan Stanley","MS", new Long(35)),
			new Item("LinkedIn Cl A","LNKD", new Long(226)),
			new Item("Hertz Global Holdings","HTZ", new Long(21)),
			new Item("Key Energy Services","KEG", new Long(3)),
			new Item("Chevron","CVX", new Long(118)),
			new Item("SunEdison","SUNE", new Long(19)),
			new Item("Weatherford International","WFT", new Long(16)),
			new Item("Chesapeake Energy","CHK", new Long(22)),
			new Item("ConocoPhillips","COP", new Long(71)),
			new Item("Freescale Semiconductor","FSL", new Long(19)),
			new Item("Peabody Energy","BTU", new Long(10)),
			new Item("Hilton Worldwide Holdings","HLT", new Long(25)),
			new Item("Sony ADS","SNE", new Long(19)),
			new Item("Tempur Sealy International","TPX", new Long(51)),
			new Item("Corning","GLW", new Long(20)),
			new Item("AngloGold Ashanti ADS","AU", new Long(8)),
			new Item("Cliffs Natural Resources","CLF", new Long(11)),
			new Item("Johnson&Johnson","JNJ", new Long(107)),
			new Item("Tesoro","TSO", new Long(71)),
			new Item("U.S. Bancorp","USB", new Long(42)),
			new Item("SandRidge Energy","SD", new Long(3)),
			new Item("Graphic Packaging Holding","GPK", new Long(11)),
			new Item("Las Vegas Sands","LVS", new Long(62)),
			new Item("Omega Healthcare Investors","OHI", new Long(38)),
			new Item("Procter&Gamble","PG", new Long(87)),
			new Item("AK Steel Holding","AKS", new Long(7)),
			new Item("Medtronic","MDT", new Long(68)),
			new Item("Turquoise Hill Resources","TRQ", new Long(3)),
			new Item("Corporate Office Properties Trust","OFC", new Long(27)),
			new Item("Salesforce.com","CRM", new Long(63)),
			new Item("DCT Industrial Trust","DCT", new Long(8)),
			new Item("SeaDrill","SDRL", new Long(22)),
			new Item("VISA Cl A","V", new Long(241)),
			new Item("Encana","ECA", new Long(18)));
		
	
	}
}
