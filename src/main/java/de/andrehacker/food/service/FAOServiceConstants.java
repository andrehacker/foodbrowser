package de.andrehacker.food.service;

/**
 * The FAO data API does not fit all our needs. Some things have to be hardcoded.
 */
public class FAOServiceConstants {

  public static final String types = "[{ \"type_id\": 5419, \"name\": \"Yield\", \"unit\": \"Hg/Ha\" }," +
      "{ \"type_id\": 5510, \"name\": \"Production\", \"unit\": \"tonnes\" }," +
      "{ \"type_id\": 5525, \"name\": \"Seed\", \"unit\": \"tonnes\" }," +
      "{ \"type_id\": 5312, \"name\": \"Area Harvested\", \"unit\": \"Ha\" }]";
  
  public static final String years = "[1961,1962,1963,1964,1965,1966,1967,1968,1969,1970,1971,1972,1973,1974,1975,1976,1977,1978,1979,1980,1981,1982,1983,1984,1985,1986,1987,1988,1989,1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012]";
  
  public static final String items = "[{ \"product_code\": 101, \"product_name\": \"Canary seed\" }," +
      "{ \"product_code\": 103, \"product_name\": \"Mixed grain\" }," +
      "{ \"product_code\": 108, \"product_name\": \"Cereals, nes\" }," +
      "{ \"product_code\": 116, \"product_name\": \"Potatoes\" }," +
      "{ \"product_code\": 122, \"product_name\": \"Sweet potatoes\" }," +
      "{ \"product_code\": 125, \"product_name\": \"Cassava\" }," +
      "{ \"product_code\": 135, \"product_name\": \"Yautia (cocoyam)\" }," +
      "{ \"product_code\": 136, \"product_name\": \"Taro (cocoyam)\" }," +
      "{ \"product_code\": 137, \"product_name\": \"Yams\" }," +
      "{ \"product_code\": 149, \"product_name\": \"Roots and Tubers, nes\" }," +
      "{ \"product_code\": 15, \"product_name\": \"Wheat\" }," +
      "{ \"product_code\": 156, \"product_name\": \"Sugar cane\" }," +
      "{ \"product_code\": 157, \"product_name\": \"Sugar beet\" }," +
      "{ \"product_code\": 161, \"product_name\": \"Sugar crops, nes\" }," +
      "{ \"product_code\": 1717, \"product_name\": \"Cereals,Total + (Total)\" }," +
      "{ \"product_code\": 1720, \"product_name\": \"Roots and Tubers,Total + (Total)\" }," +
      "{ \"product_code\": 1726, \"product_name\": \"Pulses,Total + (Total)\" }," +
      "{ \"product_code\": 1729, \"product_name\": \"Treenuts,Total + (Total)\" }," +
      "{ \"product_code\": 1732, \"product_name\": \"Oilcrops Primary + (Total)\" }," +
      "{ \"product_code\": 1735, \"product_name\": \"Vegetables Primary + (Total)\" }," +
      "{ \"product_code\": 1751, \"product_name\": \"Jute & Jute-like Fibres + (Total)\" }," +
      "{ \"product_code\": 1753, \"product_name\": \"Fibre Crops Primary + (Total)\" }," +
      "{ \"product_code\": 176, \"product_name\": \"Beans, dry\" }," +
      "{ \"product_code\": 1800, \"product_name\": \"Vegetables&Melons, Total + (Total)\" }," +
      "{ \"product_code\": 1801, \"product_name\": \"Fruit excl Melons,Total + (Total)\" }," +
      "{ \"product_code\": 1804, \"product_name\": \"Citrus Fruit,Total + (Total)\" }," +
      "{ \"product_code\": 181, \"product_name\": \"Broad beans, horse beans, dry\" }," +
      "{ \"product_code\": 1814, \"product_name\": \"Coarse Grain, Total + (Total)\" }," +
      "{ \"product_code\": 1817, \"product_name\": \"Cereals (Rice Milled Eqv + (Total)\" }," +
      "{ \"product_code\": 1841, \"product_name\": \"Oilcakes Equivalent + (Total)\" }," +
      "{ \"product_code\": 187, \"product_name\": \"Peas, dry\" }," +
      "{ \"product_code\": 191, \"product_name\": \"Chick peas\" }," +
      "{ \"product_code\": 195, \"product_name\": \"Cow peas, dry\" }," +
      "{ \"product_code\": 197, \"product_name\": \"Pigeon peas\" }," +
      "{ \"product_code\": 201, \"product_name\": \"Lentils\" }," +
      "{ \"product_code\": 203, \"product_name\": \"Bambara beans\" }," +
      "{ \"product_code\": 205, \"product_name\": \"Vetches\" }," +
      "{ \"product_code\": 210, \"product_name\": \"Lupins\" }," +
      "{ \"product_code\": 211, \"product_name\": \"Pulses, nes\" }," +
      "{ \"product_code\": 216, \"product_name\": \"Brazil nuts, with shell\" }," +
      "{ \"product_code\": 217, \"product_name\": \"Cashew nuts, with shell\" }," +
      "{ \"product_code\": 220, \"product_name\": \"Chestnuts\" }," +
      "{ \"product_code\": 221, \"product_name\": \"Almonds, with shell\" }," +
      "{ \"product_code\": 222, \"product_name\": \"Walnuts, with shell\" }," +
      "{ \"product_code\": 223, \"product_name\": \"Pistachios\" }," +
      "{ \"product_code\": 224, \"product_name\": \"Kolanuts\" }," +
      "{ \"product_code\": 225, \"product_name\": \"Hazelnuts, with shell\" }," +
      "{ \"product_code\": 226, \"product_name\": \"Arecanuts\" }," +
      "{ \"product_code\": 234, \"product_name\": \"Nuts, nes\" }," +
      "{ \"product_code\": 236, \"product_name\": \"Soybeans\" }," +
      "{ \"product_code\": 242, \"product_name\": \"Groundnuts, with shell\" }," +
      "{ \"product_code\": 249, \"product_name\": \"Coconuts\" }," +
      "{ \"product_code\": 254, \"product_name\": \"Oil palm fruit\" }," +
      "{ \"product_code\": 256, \"product_name\": \"Palm kernels\" }," +
      "{ \"product_code\": 257, \"product_name\": \"Palm oil\" }," +
      "{ \"product_code\": 260, \"product_name\": \"Olives\" }," +
      "{ \"product_code\": 263, \"product_name\": \"Karite Nuts (Sheanuts)\" }," +
      "{ \"product_code\": 265, \"product_name\": \"Castor oil seed\" }," +
      "{ \"product_code\": 267, \"product_name\": \"Sunflower seed\" }," +
      "{ \"product_code\": 27, \"product_name\": \"Rice, paddy\" }," +
      "{ \"product_code\": 270, \"product_name\": \"Rapeseed\" }," +
      "{ \"product_code\": 275, \"product_name\": \"Tung Nuts\" }," +
      "{ \"product_code\": 277, \"product_name\": \"Jojoba Seeds\" }," +
      "{ \"product_code\": 280, \"product_name\": \"Safflower seed\" }," +
      "{ \"product_code\": 289, \"product_name\": \"Sesame seed\" }," +
      "{ \"product_code\": 292, \"product_name\": \"Mustard seed\" }," +
      "{ \"product_code\": 296, \"product_name\": \"Poppy seed\" }," +
      "{ \"product_code\": 299, \"product_name\": \"Melonseed\" }," +
      "{ \"product_code\": 305, \"product_name\": \"Tallowtree Seeds\" }," +
      "{ \"product_code\": 310, \"product_name\": \"Kapok Fruit\" }," +
      "{ \"product_code\": 311, \"product_name\": \"Kapokseed in Shell\" }," +
      "{ \"product_code\": 328, \"product_name\": \"Seed cotton\" }," +
      "{ \"product_code\": 329, \"product_name\": \"Cottonseed\" }," +
      "{ \"product_code\": 333, \"product_name\": \"Linseed\" }," +
      "{ \"product_code\": 336, \"product_name\": \"Hempseed\" }," +
      "{ \"product_code\": 339, \"product_name\": \"Oilseeds, Nes\" }," +
      "{ \"product_code\": 358, \"product_name\": \"Cabbages and other brassicas\" }," +
      "{ \"product_code\": 366, \"product_name\": \"Artichokes\" }," +
      "{ \"product_code\": 367, \"product_name\": \"Asparagus\" }," +
      "{ \"product_code\": 372, \"product_name\": \"Lettuce and chicory\" }," +
      "{ \"product_code\": 373, \"product_name\": \"Spinach\" }," +
      "{ \"product_code\": 378, \"product_name\": \"Cassava leaves\" }," +
      "{ \"product_code\": 388, \"product_name\": \"Tomatoes\" }," +
      "{ \"product_code\": 393, \"product_name\": \"Cauliflowers and broccoli\" }," +
      "{ \"product_code\": 394, \"product_name\": \"Pumpkins, squash and gourds\" }," +
      "{ \"product_code\": 397, \"product_name\": \"Cucumbers and gherkins\" }," +
      "{ \"product_code\": 399, \"product_name\": \"Eggplants (aubergines)\" }," +
      "{ \"product_code\": 401, \"product_name\": \"Chillies and peppers, green\" }," +
      "{ \"product_code\": 402, \"product_name\": \"Onions (inc. shallots), green\" }," +
      "{ \"product_code\": 403, \"product_name\": \"Onions, dry\" }," +
      "{ \"product_code\": 406, \"product_name\": \"Garlic\" }," +
      "{ \"product_code\": 407, \"product_name\": \"Leeks, other alliaceous veg\" }," +
      "{ \"product_code\": 414, \"product_name\": \"Beans, green\" }," +
      "{ \"product_code\": 417, \"product_name\": \"Peas, green\" }," +
      "{ \"product_code\": 420, \"product_name\": \"Leguminous vegetables, nes\" }," +
      "{ \"product_code\": 423, \"product_name\": \"String beans\" }," +
      "{ \"product_code\": 426, \"product_name\": \"Carrots and turnips\" }," +
      "{ \"product_code\": 430, \"product_name\": \"Okra\" }," +
      "{ \"product_code\": 44, \"product_name\": \"Barley\" }," +
      "{ \"product_code\": 446, \"product_name\": \"Maize, green\" }," +
      "{ \"product_code\": 449, \"product_name\": \"Mushrooms and truffles\" }," +
      "{ \"product_code\": 459, \"product_name\": \"Chicory roots\" }," +
      "{ \"product_code\": 461, \"product_name\": \"Carobs\" }," +
      "{ \"product_code\": 463, \"product_name\": \"Vegetables fresh nes\" }," +
      "{ \"product_code\": 486, \"product_name\": \"Bananas\" }," +
      "{ \"product_code\": 489, \"product_name\": \"Plantains\" }," +
      "{ \"product_code\": 490, \"product_name\": \"Oranges\" }," +
      "{ \"product_code\": 495, \"product_name\": \"Tangerines, mandarins, clem.\" }," +
      "{ \"product_code\": 497, \"product_name\": \"Lemons and limes\" }," +
      "{ \"product_code\": 507, \"product_name\": \"Grapefruit (inc. pomelos)\" }," +
      "{ \"product_code\": 512, \"product_name\": \"Citrus fruit, nes\" }," +
      "{ \"product_code\": 515, \"product_name\": \"Apples\" }," +
      "{ \"product_code\": 521, \"product_name\": \"Pears\" }," +
      "{ \"product_code\": 523, \"product_name\": \"Quinces\" }," +
      "{ \"product_code\": 526, \"product_name\": \"Apricots\" }," +
      "{ \"product_code\": 530, \"product_name\": \"Sour cherries\" }," +
      "{ \"product_code\": 531, \"product_name\": \"Cherries\" }," +
      "{ \"product_code\": 534, \"product_name\": \"Peaches and nectarines\" }," +
      "{ \"product_code\": 536, \"product_name\": \"Plums and sloes\" }," +
      "{ \"product_code\": 541, \"product_name\": \"Stone fruit, nes\" }," +
      "{ \"product_code\": 542, \"product_name\": \"Pome fruit, nes\" }," +
      "{ \"product_code\": 544, \"product_name\": \"Strawberries\" }," +
      "{ \"product_code\": 547, \"product_name\": \"Raspberries\" }," +
      "{ \"product_code\": 549, \"product_name\": \"Gooseberries\" }," +
      "{ \"product_code\": 550, \"product_name\": \"Currants\" }," +
      "{ \"product_code\": 552, \"product_name\": \"Blueberries\" }," +
      "{ \"product_code\": 554, \"product_name\": \"Cranberries\" }," +
      "{ \"product_code\": 558, \"product_name\": \"Berries Nes\" }," +
      "{ \"product_code\": 56, \"product_name\": \"Maize\" }," +
      "{ \"product_code\": 560, \"product_name\": \"Grapes\" }," +
      "{ \"product_code\": 567, \"product_name\": \"Watermelons\" }," +
      "{ \"product_code\": 568, \"product_name\": \"Other melons (inc.cantaloupes)\" }," +
      "{ \"product_code\": 569, \"product_name\": \"Figs\" }," +
      "{ \"product_code\": 571, \"product_name\": \"Mangoes, mangosteens, guavas\" }," +
      "{ \"product_code\": 572, \"product_name\": \"Avocados\" }," +
      "{ \"product_code\": 574, \"product_name\": \"Pineapples\" }," +
      "{ \"product_code\": 577, \"product_name\": \"Dates\" }," +
      "{ \"product_code\": 587, \"product_name\": \"Persimmons\" }," +
      "{ \"product_code\": 591, \"product_name\": \"Cashewapple\" }," +
      "{ \"product_code\": 592, \"product_name\": \"Kiwi fruit\" }," +
      "{ \"product_code\": 600, \"product_name\": \"Papayas\" }," +
      "{ \"product_code\": 603, \"product_name\": \"Fruit, tropical fresh nes\" }," +
      "{ \"product_code\": 619, \"product_name\": \"Fruit Fresh Nes\" }," +
      "{ \"product_code\": 656, \"product_name\": \"Coffee, green\" }," +
      "{ \"product_code\": 661, \"product_name\": \"Cocoa beans\" }," +
      "{ \"product_code\": 667, \"product_name\": \"Tea\" }," +
      "{ \"product_code\": 671, \"product_name\": \"Maté\" }," +
      "{ \"product_code\": 677, \"product_name\": \"Hops\" }," +
      "{ \"product_code\": 68, \"product_name\": \"Popcorn\" }," +
      "{ \"product_code\": 687, \"product_name\": \"Pepper (Piper spp.)\" }," +
      "{ \"product_code\": 689, \"product_name\": \"Chillies and peppers, dry\" }," +
      "{ \"product_code\": 692, \"product_name\": \"Vanilla\" }," +
      "{ \"product_code\": 693, \"product_name\": \"Cinnamon (canella)\" }," +
      "{ \"product_code\": 698, \"product_name\": \"Cloves\" }," +
      "{ \"product_code\": 702, \"product_name\": \"Nutmeg, mace and cardamoms\" }," +
      "{ \"product_code\": 71, \"product_name\": \"Rye\" }," +
      "{ \"product_code\": 711, \"product_name\": \"Anise, badian, fennel, corian.\" }," +
      "{ \"product_code\": 720, \"product_name\": \"Ginger\" }," +
      "{ \"product_code\": 723, \"product_name\": \"Spices, nes\" }," +
      "{ \"product_code\": 748, \"product_name\": \"Peppermint\" }," +
      "{ \"product_code\": 75, \"product_name\": \"Oats\" }," +
      "{ \"product_code\": 754, \"product_name\": \"Pyrethrum,Dried\" }," +
      "{ \"product_code\": 767, \"product_name\": \"Cotton lint\" }," +
      "{ \"product_code\": 773, \"product_name\": \"Flax fibre and tow\" }," +
      "{ \"product_code\": 777, \"product_name\": \"Hemp Tow Waste\" }," +
      "{ \"product_code\": 778, \"product_name\": \"Kapok Fibre\" }," +
      "{ \"product_code\": 780, \"product_name\": \"Jute\" }," +
      "{ \"product_code\": 782, \"product_name\": \"Other Bastfibres\" }," +
      "{ \"product_code\": 788, \"product_name\": \"Ramie\" }," +
      "{ \"product_code\": 789, \"product_name\": \"Sisal\" }," +
      "{ \"product_code\": 79, \"product_name\": \"Millet\" }," +
      "{ \"product_code\": 800, \"product_name\": \"Agave Fibres Nes\" }," +
      "{ \"product_code\": 809, \"product_name\": \"Manila Fibre (Abaca)\" }," +
      "{ \"product_code\": 813, \"product_name\": \"Coir\" }," +
      "{ \"product_code\": 821, \"product_name\": \"Fibre Crops Nes\" }," +
      "{ \"product_code\": 826, \"product_name\": \"Tobacco, unmanufactured\" }," +
      "{ \"product_code\": 83, \"product_name\": \"Sorghum\" }," +
      "{ \"product_code\": 836, \"product_name\": \"Natural rubber\" }," +
      "{ \"product_code\": 839, \"product_name\": \"Gums Natural\" }," +
      "{ \"product_code\": 89, \"product_name\": \"Buckwheat\" }," +
      "{ \"product_code\": 92, \"product_name\": \"Quinoa\" }," +
      "{ \"product_code\": 94, \"product_name\": \"Fonio\" }," +
      "{ \"product_code\": 97, \"product_name\": \"Triticale\" }]";
  
  public static final String countries = "[{ \"country_code\": 1, \"iso_code\": \"AM\", \"country_name\": \"Armenia\" }," +
      "{ \"country_code\": 2, \"iso_code\": \"AF\", \"country_name\": \"Afghanistan\" }," +
      "{ \"country_code\": 3, \"iso_code\": \"AL\", \"country_name\": \"Albania\" }," +
      "{ \"country_code\": 4, \"iso_code\": \"DZ\", \"country_name\": \"Algeria\" }," +
      "{ \"country_code\": 5, \"iso_code\": \"AS\", \"country_name\": \"American Samoa\" }," +
      "{ \"country_code\": 6, \"iso_code\": \"AD\", \"country_name\": \"Andorra\" }," +
      "{ \"country_code\": 7, \"iso_code\": \"AO\", \"country_name\": \"Angola\" }," +
      "{ \"country_code\": 8, \"iso_code\": \"AG\", \"country_name\": \"Antigua and Barbuda\" }," +
      "{ \"country_code\": 9, \"iso_code\": \"AR\", \"country_name\": \"Argentina\" }," +
      "{ \"country_code\": 10, \"iso_code\": \"AU\", \"country_name\": \"Australia\" }," +
      "{ \"country_code\": 11, \"iso_code\": \"AT\", \"country_name\": \"Austria\" }," +
      "{ \"country_code\": 12, \"iso_code\": \"BS\", \"country_name\": \"Bahamas\" }," +
      "{ \"country_code\": 13, \"iso_code\": \"BH\", \"country_name\": \"Bahrain\" }," +
      "{ \"country_code\": 14, \"iso_code\": \"BB\", \"country_name\": \"Barbados\" }," +
      "{ \"country_code\": 15, \"iso_code\": \"..\", \"country_name\": \"Belgium-Luxembourg\" }," +
      "{ \"country_code\": 16, \"iso_code\": \"BD\", \"country_name\": \"Bangladesh\" }," +
      "{ \"country_code\": 17, \"iso_code\": \"BM\", \"country_name\": \"Bermuda\" }," +
      "{ \"country_code\": 18, \"iso_code\": \"BT\", \"country_name\": \"Bhutan\" }," +
      "{ \"country_code\": 19, \"iso_code\": \"BO\", \"country_name\": \"Bolivia\" }," +
      "{ \"country_code\": 20, \"iso_code\": \"BW\", \"country_name\": \"Botswana\" }," +
      "{ \"country_code\": 21, \"iso_code\": \"BR\", \"country_name\": \"Brazil\" }," +
      "{ \"country_code\": 22, \"iso_code\": \"AW\", \"country_name\": \"Aruba\" }," +
      "{ \"country_code\": 23, \"iso_code\": \"BZ\", \"country_name\": \"Belize\" }," +
      "{ \"country_code\": 24, \"iso_code\": \"IO\", \"country_name\": \"British Indian Ocean Territory\" }," +
      "{ \"country_code\": 25, \"iso_code\": \"SB\", \"country_name\": \"Solomon Islands\" }," +
      "{ \"country_code\": 26, \"iso_code\": \"BN\", \"country_name\": \"Brunei Darussalam\" }," +
      "{ \"country_code\": 27, \"iso_code\": \"BG\", \"country_name\": \"Bulgaria\" }," +
      "{ \"country_code\": 28, \"iso_code\": \"MM\", \"country_name\": \"Myanmar\" }," +
      "{ \"country_code\": 29, \"iso_code\": \"BI\", \"country_name\": \"Burundi\" }," +
      "{ \"country_code\": 30, \"iso_code\": \"AQ\", \"country_name\": \"Antarctica\" }," +
      "{ \"country_code\": 32, \"iso_code\": \"CM\", \"country_name\": \"Cameroon\" }," +
      "{ \"country_code\": 33, \"iso_code\": \"CA\", \"country_name\": \"Canada\" }," +
      "{ \"country_code\": 35, \"iso_code\": \"CV\", \"country_name\": \"Cabo Verde\" }," +
      "{ \"country_code\": 36, \"iso_code\": \"KY\", \"country_name\": \"Cayman Islands\" }," +
      "{ \"country_code\": 37, \"iso_code\": \"CF\", \"country_name\": \"Central African Republic\" }," +
      "{ \"country_code\": 38, \"iso_code\": \"LK\", \"country_name\": \"Sri Lanka\" }," +
      "{ \"country_code\": 39, \"iso_code\": \"TD\", \"country_name\": \"Chad\" }," +
      "{ \"country_code\": 40, \"iso_code\": \"CL\", \"country_name\": \"Chile\" }," +
      "{ \"country_code\": 41, \"iso_code\": \"CN\", \"country_name\": \"China, mainland\" }," +
      "{ \"country_code\": 42, \"iso_code\": \"CX\", \"country_name\": \"Christmas Island\" }," +
      "{ \"country_code\": 43, \"iso_code\": \"CC\", \"country_name\": \"Cocos (Keeling) Islands\" }," +
      "{ \"country_code\": 44, \"iso_code\": \"CO\", \"country_name\": \"Colombia\" }," +
      "{ \"country_code\": 45, \"iso_code\": \"KM\", \"country_name\": \"Comoros\" }," +
      "{ \"country_code\": 46, \"iso_code\": \"CG\", \"country_name\": \"Congo\" }," +
      "{ \"country_code\": 47, \"iso_code\": \"CK\", \"country_name\": \"Cook Islands\" }," +
      "{ \"country_code\": 48, \"iso_code\": \"CR\", \"country_name\": \"Costa Rica\" }," +
      "{ \"country_code\": 49, \"iso_code\": \"CU\", \"country_name\": \"Cuba\" }," +
      "{ \"country_code\": 50, \"iso_code\": \"CY\", \"country_name\": \"Cyprus\" }," +
      "{ \"country_code\": 51, \"iso_code\": \"..\", \"country_name\": \"Czechoslovakia\" }," +
      "{ \"country_code\": 52, \"iso_code\": \"AZ\", \"country_name\": \"Azerbaijan\" }," +
      "{ \"country_code\": 53, \"iso_code\": \"BJ\", \"country_name\": \"Benin\" }," +
      "{ \"country_code\": 54, \"iso_code\": \"DK\", \"country_name\": \"Denmark\" }," +
      "{ \"country_code\": 55, \"iso_code\": \"DM\", \"country_name\": \"Dominica\" }," +
      "{ \"country_code\": 56, \"iso_code\": \"DO\", \"country_name\": \"Dominican Republic\" }," +
      "{ \"country_code\": 57, \"iso_code\": \"BY\", \"country_name\": \"Belarus\" }," +
      "{ \"country_code\": 58, \"iso_code\": \"EC\", \"country_name\": \"Ecuador\" }," +
      "{ \"country_code\": 59, \"iso_code\": \"EG\", \"country_name\": \"Egypt\" }," +
      "{ \"country_code\": 60, \"iso_code\": \"SV\", \"country_name\": \"El Salvador\" }," +
      "{ \"country_code\": 61, \"iso_code\": \"GQ\", \"country_name\": \"Equatorial Guinea\" }," +
      "{ \"country_code\": 62, \"iso_code\": \"..\", \"country_name\": \"Ethiopia PDR\" }," +
      "{ \"country_code\": 63, \"iso_code\": \"EE\", \"country_name\": \"Estonia\" }," +
      "{ \"country_code\": 64, \"iso_code\": \"FO\", \"country_name\": \"Faroe Islands\" }," +
      "{ \"country_code\": 65, \"iso_code\": \"FK\", \"country_name\": \"Falkland Islands\" }," +
      "{ \"country_code\": 66, \"iso_code\": \"FJ\", \"country_name\": \"Fiji\" }," +
      "{ \"country_code\": 67, \"iso_code\": \"FI\", \"country_name\": \"Finland\" }," +
      "{ \"country_code\": 68, \"iso_code\": \"FR\", \"country_name\": \"France\" }," +
      "{ \"country_code\": 69, \"iso_code\": \"GF\", \"country_name\": \"French Guiana\" }," +
      "{ \"country_code\": 70, \"iso_code\": \"PF\", \"country_name\": \"French Polynesia\" }," +
      "{ \"country_code\": 71, \"iso_code\": \"TF\", \"country_name\": \"French Southern and Antarctic Territories\" }," +
      "{ \"country_code\": 72, \"iso_code\": \"DJ\", \"country_name\": \"Djibouti\" }," +
      "{ \"country_code\": 73, \"iso_code\": \"GE\", \"country_name\": \"Georgia\" }," +
      "{ \"country_code\": 74, \"iso_code\": \"GA\", \"country_name\": \"Gabon\" }," +
      "{ \"country_code\": 75, \"iso_code\": \"GM\", \"country_name\": \"Gambia\" }," +
      "{ \"country_code\": 79, \"iso_code\": \"DE\", \"country_name\": \"Germany\" }," +
      "{ \"country_code\": 80, \"iso_code\": \"BA\", \"country_name\": \"Bosnia and Herzegovina\" }," +
      "{ \"country_code\": 81, \"iso_code\": \"GH\", \"country_name\": \"Ghana\" }," +
      "{ \"country_code\": 82, \"iso_code\": \"GI\", \"country_name\": \"Gibraltar\" }," +
      "{ \"country_code\": 83, \"iso_code\": \"KI\", \"country_name\": \"Kiribati\" }," +
      "{ \"country_code\": 84, \"iso_code\": \"GR\", \"country_name\": \"Greece\" }," +
      "{ \"country_code\": 85, \"iso_code\": \"GL\", \"country_name\": \"Greenland\" }," +
      "{ \"country_code\": 86, \"iso_code\": \"GD\", \"country_name\": \"Grenada\" }," +
      "{ \"country_code\": 87, \"iso_code\": \"GP\", \"country_name\": \"Guadeloupe\" }," +
      "{ \"country_code\": 88, \"iso_code\": \"GU\", \"country_name\": \"Guam\" }," +
      "{ \"country_code\": 89, \"iso_code\": \"GT\", \"country_name\": \"Guatemala\" }," +
      "{ \"country_code\": 90, \"iso_code\": \"GN\", \"country_name\": \"Guinea\" }," +
      "{ \"country_code\": 91, \"iso_code\": \"GY\", \"country_name\": \"Guyana\" }," +
      "{ \"country_code\": 93, \"iso_code\": \"HT\", \"country_name\": \"Haiti\" }," +
      "{ \"country_code\": 94, \"iso_code\": \"VA\", \"country_name\": \"Holy See\" }," +
      "{ \"country_code\": 95, \"iso_code\": \"HN\", \"country_name\": \"Honduras\" }," +
      "{ \"country_code\": 96, \"iso_code\": \"HK\", \"country_name\": \"China, Hong Kong SAR\" }," +
      "{ \"country_code\": 97, \"iso_code\": \"HU\", \"country_name\": \"Hungary\" }," +
      "{ \"country_code\": 98, \"iso_code\": \"HR\", \"country_name\": \"Croatia\" }," +
      "{ \"country_code\": 99, \"iso_code\": \"IS\", \"country_name\": \"Iceland\" }," +
      "{ \"country_code\": 100, \"iso_code\": \"IN\", \"country_name\": \"India\" }," +
      "{ \"country_code\": 101, \"iso_code\": \"ID\", \"country_name\": \"Indonesia\" }," +
      "{ \"country_code\": 102, \"iso_code\": \"IR\", \"country_name\": \"Iran\" }," +
      "{ \"country_code\": 103, \"iso_code\": \"IQ\", \"country_name\": \"Iraq\" }," +
      "{ \"country_code\": 104, \"iso_code\": \"IE\", \"country_name\": \"Ireland\" }," +
      "{ \"country_code\": 105, \"iso_code\": \"IL\", \"country_name\": \"Israel\" }," +
      "{ \"country_code\": 106, \"iso_code\": \"IT\", \"country_name\": \"Italy\" }," +
      "{ \"country_code\": 107, \"iso_code\": \"CI\", \"country_name\": \"Côte d Ivoire\" }," +
      "{ \"country_code\": 108, \"iso_code\": \"KZ\", \"country_name\": \"Kazakhstan\" }," +
      "{ \"country_code\": 109, \"iso_code\": \"JM\", \"country_name\": \"Jamaica\" }," +
      "{ \"country_code\": 110, \"iso_code\": \"JP\", \"country_name\": \"Japan\" }," +
      "{ \"country_code\": 112, \"iso_code\": \"JO\", \"country_name\": \"Jordan\" }," +
      "{ \"country_code\": 113, \"iso_code\": \"KG\", \"country_name\": \"Kyrgyzstan\" }," +
      "{ \"country_code\": 114, \"iso_code\": \"KE\", \"country_name\": \"Kenya\" }," +
      "{ \"country_code\": 219, \"iso_code\": \"TO\", \"country_name\": \"Tonga\" }," +
      "{ \"country_code\": 115, \"iso_code\": \"KH\", \"country_name\": \"Cambodia\" }," +
      "{ \"country_code\": 116, \"iso_code\": \"KP\", \"country_name\": \"Democratic Peoples Republic of Korea\" }," +
      "{ \"country_code\": 117, \"iso_code\": \"KR\", \"country_name\": \"Republic of Korea\" }," +
      "{ \"country_code\": 118, \"iso_code\": \"KW\", \"country_name\": \"Kuwait\" }," +
      "{ \"country_code\": 119, \"iso_code\": \"LV\", \"country_name\": \"Latvia\" }," +
      "{ \"country_code\": 120, \"iso_code\": \"LA\", \"country_name\": \"Lao Peoples Democratic Republic\" }," +
      "{ \"country_code\": 121, \"iso_code\": \"LB\", \"country_name\": \"Lebanon\" }," +
      "{ \"country_code\": 122, \"iso_code\": \"LS\", \"country_name\": \"Lesotho\" }," +
      "{ \"country_code\": 123, \"iso_code\": \"LR\", \"country_name\": \"Liberia\" }," +
      "{ \"country_code\": 124, \"iso_code\": \"LY\", \"country_name\": \"Libya\" }," +
      "{ \"country_code\": 125, \"iso_code\": \"LI\", \"country_name\": \"Liechtenstein\" }," +
      "{ \"country_code\": 126, \"iso_code\": \"LT\", \"country_name\": \"Lithuania\" }," +
      "{ \"country_code\": 127, \"iso_code\": \"MH\", \"country_name\": \"Marshall Islands\" }," +
      "{ \"country_code\": 128, \"iso_code\": \"MO\", \"country_name\": \"China, Macao SAR\" }," +
      "{ \"country_code\": 129, \"iso_code\": \"MG\", \"country_name\": \"Madagascar\" }," +
      "{ \"country_code\": 130, \"iso_code\": \"MW\", \"country_name\": \"Malawi\" }," +
      "{ \"country_code\": 131, \"iso_code\": \"MY\", \"country_name\": \"Malaysia\" }," +
      "{ \"country_code\": 132, \"iso_code\": \"MV\", \"country_name\": \"Maldives\" }," +
      "{ \"country_code\": 133, \"iso_code\": \"ML\", \"country_name\": \"Mali\" }," +
      "{ \"country_code\": 134, \"iso_code\": \"MT\", \"country_name\": \"Malta\" }," +
      "{ \"country_code\": 135, \"iso_code\": \"MQ\", \"country_name\": \"Martinique\" }," +
      "{ \"country_code\": 136, \"iso_code\": \"MR\", \"country_name\": \"Mauritania\" }," +
      "{ \"country_code\": 137, \"iso_code\": \"MU\", \"country_name\": \"Mauritius\" }," +
      "{ \"country_code\": 138, \"iso_code\": \"MX\", \"country_name\": \"Mexico\" }," +
      "{ \"country_code\": 140, \"iso_code\": \"MC\", \"country_name\": \"Monaco\" }," +
      "{ \"country_code\": 141, \"iso_code\": \"MN\", \"country_name\": \"Mongolia\" }," +
      "{ \"country_code\": 142, \"iso_code\": \"MS\", \"country_name\": \"Montserrat\" }," +
      "{ \"country_code\": 143, \"iso_code\": \"MA\", \"country_name\": \"Morocco\" }," +
      "{ \"country_code\": 144, \"iso_code\": \"MZ\", \"country_name\": \"Mozambique\" }," +
      "{ \"country_code\": 145, \"iso_code\": \"FM\", \"country_name\": \"Micronesia\" }," +
      "{ \"country_code\": 146, \"iso_code\": \"MD\", \"country_name\": \"Republic of Moldova\" }," +
      "{ \"country_code\": 147, \"iso_code\": \"NA\", \"country_name\": \"Namibia\" }," +
      "{ \"country_code\": 148, \"iso_code\": \"NR\", \"country_name\": \"Nauru\" }," +
      "{ \"country_code\": 149, \"iso_code\": \"NP\", \"country_name\": \"Nepal\" }," +
      "{ \"country_code\": 150, \"iso_code\": \"NL\", \"country_name\": \"Netherlands\" }," +
      "{ \"country_code\": 151, \"iso_code\": \"AN\", \"country_name\": \"Netherlands Antilles\" }," +
      "{ \"country_code\": 153, \"iso_code\": \"NC\", \"country_name\": \"New Caledonia\" }," +
      "{ \"country_code\": 154, \"iso_code\": \"MK\", \"country_name\": \"The former Yugoslav Republic of Macedonia\" }," +
      "{ \"country_code\": 155, \"iso_code\": \"VU\", \"country_name\": \"Vanuatu\" }," +
      "{ \"country_code\": 156, \"iso_code\": \"NZ\", \"country_name\": \"New Zealand\" }," +
      "{ \"country_code\": 157, \"iso_code\": \"NI\", \"country_name\": \"Nicaragua\" }," +
      "{ \"country_code\": 158, \"iso_code\": \"NE\", \"country_name\": \"Niger\" }," +
      "{ \"country_code\": 159, \"iso_code\": \"NG\", \"country_name\": \"Nigeria\" }," +
      "{ \"country_code\": 160, \"iso_code\": \"NU\", \"country_name\": \"Niue\" }," +
      "{ \"country_code\": 161, \"iso_code\": \"NF\", \"country_name\": \"Norfolk Island\" }," +
      "{ \"country_code\": 162, \"iso_code\": \"NO\", \"country_name\": \"Norway\" }," +
      "{ \"country_code\": 163, \"iso_code\": \"MP\", \"country_name\": \"Northern Mariana Islands\" }," +
      "{ \"country_code\": 164, \"iso_code\": \"..\", \"country_name\": \"Pacific Islands Trust Territory\" }," +
      "{ \"country_code\": 165, \"iso_code\": \"PK\", \"country_name\": \"Pakistan\" }," +
      "{ \"country_code\": 166, \"iso_code\": \"PA\", \"country_name\": \"Panama\" }," +
      "{ \"country_code\": 167, \"iso_code\": \"CZ\", \"country_name\": \"Czech Republic\" }," +
      "{ \"country_code\": 168, \"iso_code\": \"PG\", \"country_name\": \"Papua New Guinea\" }," +
      "{ \"country_code\": 169, \"iso_code\": \"PY\", \"country_name\": \"Paraguay\" }," +
      "{ \"country_code\": 170, \"iso_code\": \"PE\", \"country_name\": \"Peru\" }," +
      "{ \"country_code\": 171, \"iso_code\": \"PH\", \"country_name\": \"Philippines\" }," +
      "{ \"country_code\": 172, \"iso_code\": \"PN\", \"country_name\": \"Pitcairn Islands\" }," +
      "{ \"country_code\": 173, \"iso_code\": \"PL\", \"country_name\": \"Poland\" }," +
      "{ \"country_code\": 174, \"iso_code\": \"PT\", \"country_name\": \"Portugal\" }," +
      "{ \"country_code\": 175, \"iso_code\": \"GW\", \"country_name\": \"Guinea-Bissau\" }," +
      "{ \"country_code\": 176, \"iso_code\": \"TL\", \"country_name\": \"Timor-Leste\" }," +
      "{ \"country_code\": 177, \"iso_code\": \"PR\", \"country_name\": \"Puerto Rico\" }," +
      "{ \"country_code\": 178, \"iso_code\": \"ER\", \"country_name\": \"Eritrea\" }," +
      "{ \"country_code\": 179, \"iso_code\": \"QA\", \"country_name\": \"Qatar\" }," +
      "{ \"country_code\": 180, \"iso_code\": \"PW\", \"country_name\": \"Palau\" }," +
      "{ \"country_code\": 181, \"iso_code\": \"ZW\", \"country_name\": \"Zimbabwe\" }," +
      "{ \"country_code\": 182, \"iso_code\": \"RE\", \"country_name\": \"Réunion\" }," +
      "{ \"country_code\": 183, \"iso_code\": \"RO\", \"country_name\": \"Romania\" }," +
      "{ \"country_code\": 184, \"iso_code\": \"RW\", \"country_name\": \"Rwanda\" }," +
      "{ \"country_code\": 185, \"iso_code\": \"RU\", \"country_name\": \"Russian Federation\" }," +
      "{ \"country_code\": 186, \"iso_code\": \"CS\", \"country_name\": \"Serbia and Montenegro\" }," +
      "{ \"country_code\": 187, \"iso_code\": \"SH\", \"country_name\": \"Saint Helena\" }," +
      "{ \"country_code\": 188, \"iso_code\": \"KN\", \"country_name\": \"Saint Kitts and Nevis\" }," +
      "{ \"country_code\": 189, \"iso_code\": \"LC\", \"country_name\": \"Saint Lucia\" }," +
      "{ \"country_code\": 190, \"iso_code\": \"PM\", \"country_name\": \"Saint Pierre and Miquelon\" }," +
      "{ \"country_code\": 191, \"iso_code\": \"VC\", \"country_name\": \"Saint Vincent and the Grenadines\" }," +
      "{ \"country_code\": 192, \"iso_code\": \"SM\", \"country_name\": \"San Marino\" }," +
      "{ \"country_code\": 193, \"iso_code\": \"ST\", \"country_name\": \"Sao Tome and Principe\" }," +
      "{ \"country_code\": 194, \"iso_code\": \"SA\", \"country_name\": \"Saudi Arabia\" }," +
      "{ \"country_code\": 195, \"iso_code\": \"SN\", \"country_name\": \"Senegal\" }," +
      "{ \"country_code\": 196, \"iso_code\": \"SC\", \"country_name\": \"Seychelles\" }," +
      "{ \"country_code\": 197, \"iso_code\": \"SL\", \"country_name\": \"Sierra Leone\" }," +
      "{ \"country_code\": 198, \"iso_code\": \"SI\", \"country_name\": \"Slovenia\" }," +
      "{ \"country_code\": 199, \"iso_code\": \"SK\", \"country_name\": \"Slovakia\" }," +
      "{ \"country_code\": 200, \"iso_code\": \"SG\", \"country_name\": \"Singapore\" }," +
      "{ \"country_code\": 201, \"iso_code\": \"SO\", \"country_name\": \"Somalia\" }," +
      "{ \"country_code\": 202, \"iso_code\": \"ZA\", \"country_name\": \"South Africa\" }," +
      "{ \"country_code\": 203, \"iso_code\": \"ES\", \"country_name\": \"Spain\" }," +
      "{ \"country_code\": 205, \"iso_code\": \"EH\", \"country_name\": \"Western Sahara\" }," +
      "{ \"country_code\": 206, \"iso_code\": \"SD\", \"country_name\": \"Sudan (former)\" }," +
      "{ \"country_code\": 207, \"iso_code\": \"SR\", \"country_name\": \"Suriname\" }," +
      "{ \"country_code\": 208, \"iso_code\": \"TJ\", \"country_name\": \"Tajikistan\" }," +
      "{ \"country_code\": 209, \"iso_code\": \"SZ\", \"country_name\": \"Swaziland\" }," +
      "{ \"country_code\": 210, \"iso_code\": \"SE\", \"country_name\": \"Sweden\" }," +
      "{ \"country_code\": 211, \"iso_code\": \"CH\", \"country_name\": \"Switzerland\" }," +
      "{ \"country_code\": 212, \"iso_code\": \"SY\", \"country_name\": \"Syrian Arab Republic\" }," +
      "{ \"country_code\": 213, \"iso_code\": \"TM\", \"country_name\": \"Turkmenistan\" }," +
      "{ \"country_code\": 215, \"iso_code\": \"TZ\", \"country_name\": \"United Republic of Tanzania\" }," +
      "{ \"country_code\": 216, \"iso_code\": \"TH\", \"country_name\": \"Thailand\" }," +
      "{ \"country_code\": 217, \"iso_code\": \"TG\", \"country_name\": \"Togo\" }," +
      "{ \"country_code\": 218, \"iso_code\": \"TK\", \"country_name\": \"Tokelau\" }," +
      "{ \"country_code\": 220, \"iso_code\": \"TT\", \"country_name\": \"Trinidad and Tobago\" }," +
      "{ \"country_code\": 221, \"iso_code\": \"OM\", \"country_name\": \"Oman\" }," +
      "{ \"country_code\": 222, \"iso_code\": \"TN\", \"country_name\": \"Tunisia\" }," +
      "{ \"country_code\": 223, \"iso_code\": \"TR\", \"country_name\": \"Turkey\" }," +
      "{ \"country_code\": 224, \"iso_code\": \"TC\", \"country_name\": \"Turks and Caicos Islands\" }," +
      "{ \"country_code\": 225, \"iso_code\": \"AE\", \"country_name\": \"United Arab Emirates\" }," +
      "{ \"country_code\": 226, \"iso_code\": \"UG\", \"country_name\": \"Uganda\" }," +
      "{ \"country_code\": 227, \"iso_code\": \"TV\", \"country_name\": \"Tuvalu\" }," +
      "{ \"country_code\": 228, \"iso_code\": \"..\", \"country_name\": \"USSR\" }," +
      "{ \"country_code\": 229, \"iso_code\": \"GB\", \"country_name\": \"United Kingdom\" }," +
      "{ \"country_code\": 230, \"iso_code\": \"UA\", \"country_name\": \"Ukraine\" }," +
      "{ \"country_code\": 231, \"iso_code\": \"US\", \"country_name\": \"United States of America\" }," +
      "{ \"country_code\": 233, \"iso_code\": \"BF\", \"country_name\": \"Burkina Faso\" }," +
      "{ \"country_code\": 234, \"iso_code\": \"UY\", \"country_name\": \"Uruguay\" }," +
      "{ \"country_code\": 235, \"iso_code\": \"UZ\", \"country_name\": \"Uzbekistan\" }," +
      "{ \"country_code\": 236, \"iso_code\": \"VE\", \"country_name\": \"Venezuela\" }," +
      "{ \"country_code\": 237, \"iso_code\": \"VN\", \"country_name\": \"Viet Nam\" }," +
      "{ \"country_code\": 238, \"iso_code\": \"ET\", \"country_name\": \"Ethiopia\" }," +
      "{ \"country_code\": 239, \"iso_code\": \"VG\", \"country_name\": \"British Virgin Islands\" }," +
      "{ \"country_code\": 240, \"iso_code\": \"VI\", \"country_name\": \"United States Virgin Islands\" }," +
      "{ \"country_code\": 242, \"iso_code\": \"WK\", \"country_name\": \"Wake Island\" }," +
      "{ \"country_code\": 243, \"iso_code\": \"WF\", \"country_name\": \"Wallis and Futuna Islands\" }," +
      "{ \"country_code\": 244, \"iso_code\": \"WS\", \"country_name\": \"Samoa\" }," +
      "{ \"country_code\": 247, \"iso_code\": \"..\", \"country_name\": \"Yemen Dem\" }," +
      "{ \"country_code\": 248, \"iso_code\": \"..\", \"country_name\": \"Yugoslav SFR\" }," +
      "{ \"country_code\": 249, \"iso_code\": \"YE\", \"country_name\": \"Yemen\" }," +
      "{ \"country_code\": 250, \"iso_code\": \"CD\", \"country_name\": \"Democratic Republic of the Congo\" }," +
      "{ \"country_code\": 251, \"iso_code\": \"ZM\", \"country_name\": \"Zambia\" }," +
      "{ \"country_code\": 252, \"iso_code\": \" \", \"country_name\": \"Unspecified\" }," +
      "{ \"country_code\": 254, \"iso_code\": \" \", \"country_name\": \"Others (adjustment)\" }," +
      "{ \"country_code\": 255, \"iso_code\": \"BE\", \"country_name\": \"Belgium\" }," +
      "{ \"country_code\": 256, \"iso_code\": \"LU\", \"country_name\": \"Luxembourg\" }," +
      "{ \"country_code\": 258, \"iso_code\": \"AI\", \"country_name\": \"Anguilla\" }," +
      "{ \"country_code\": 259, \"iso_code\": \"..\", \"country_name\": \"Channel Islands\" }," +
      "{ \"country_code\": 260, \"iso_code\": \"SJ\", \"country_name\": \"Svalbard and Jan Mayen Islands\" }," +
      "{ \"country_code\": 264, \"iso_code\": \"IM\", \"country_name\": \"Isle of Man\" }," +
      "{ \"country_code\": 270, \"iso_code\": \"YT\", \"country_name\": \"Mayotte\" }," +
      "{ \"country_code\": 272, \"iso_code\": \"RS\", \"country_name\": \"Serbia\" }," +
      "{ \"country_code\": 273, \"iso_code\": \"ME\", \"country_name\": \"Montenegro\" }," +
      "{ \"country_code\": 276, \"iso_code\": \"SD\", \"country_name\": \"Sudan\" }," +
      "{ \"country_code\": 277, \"iso_code\": \"SS\", \"country_name\": \"South Sudan\" }," +
      "{ \"country_code\": 279, \"iso_code\": \" \", \"country_name\": \"Curaçao\" }," +
      "{ \"country_code\": 280, \"iso_code\": \" \", \"country_name\": \"Sint Maarten (Dutch Part)\" }," +
      "{ \"country_code\": 281, \"iso_code\": \" \", \"country_name\": \"Saint-Martin (French Part)\" }," +
      "{ \"country_code\": 299, \"iso_code\": \"..\", \"country_name\": \"Occupied Palestinian Territory\" }," +
      "{ \"country_code\": 351, \"iso_code\": \" \", \"country_name\": \"China\" }," +
      "{ \"country_code\": 214, \"iso_code\": \"TW\", \"country_name\": \"Taiwan\" }]";

}
