package com.sgp.packsmart.Data;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.sgp.packsmart.Database.RoomDB;
import com.sgp.packsmart.Models.Items;
import com.sgp.packsmart.MyConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {

    RoomDB database;
    String category;
    Context context;

    public static  final String LAST_VERSION = "LAST_VERSION";
    public static  final int NEW_VERSION = 1;

    public  AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

    public List<Items> getBasicData() {
         category = "Essentials";
         List<Items> basicItem = new ArrayList<>();
         basicItem.add(new Items("Visa",category, false));
        basicItem.add(new Items("Passport",category, false));
        basicItem.add(new Items("pen",category, false));
        basicItem.add(new Items("Tickets",category, false));
        basicItem.add(new Items("Wallet",category, false));
        basicItem.add(new Items("Currency",category, false));
        basicItem.add(new Items("HouseKey",category, false));
        basicItem.add(new Items("Book",category, false));
        basicItem.add(new Items("Travel Pillow",category, false));
        basicItem.add(new Items("Eye Patch",category, false));
        basicItem.add(new Items("Umbrella",category, false));
        return basicItem;
    }

    public List<Items> getClothingData() {
        String []data = {"T-Shirts", "Shirts", "Pajamas", "Jacket", "Casual Dress",
                "Under Wear","Evening Dress","Skirt","Trousers", "Jeans","Shorts",
                "RainCoat","Hat","Scarf","Belt","Slipper","Sneakers","Shoes","Winter Wear"};
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE,data);
    }

    public List<Items> getBeautyData() {
        String []data = {"Perfume", "Lip Cream", "Moisturizer", "Sun Screen", "Nail Polish",
                "Nail Cutter","Ear Buds","Cotton","Wet Wipes", "Hair Clip","Comb",
                "Hair Dryer"};
        return prepareItemsList(MyConstants.BEAUTY_CAMEL_CASE,data);
    }

    public List<Items> getToiletriesData() {
        String []data = {"Tooth Paste", "Tooth Brush", "Face Wash", "Shaving Cream", "Razor Blade",
                "Body Wash","Shampoo","Conditioner","Pad"};
        return prepareItemsList(MyConstants.TOILETRIES_CAMEL_CASE,data);
    }

    public List<Items> getMedicineData() {
        String []data = {"Paracetamol","Aspirin","Pain Killer","Moov spray","Dettol","Sanitizer",
        "Hot Water Bag","First Aid Kit","Glucose"};
        return prepareItemsList(MyConstants.MEDICINE_CAMEL_CASE,data);
    }

    public List<Items> getBabyNeedsData() {
        String []data = {"Diaper","Outfit","Blanket","Baby Bottles","Water Bottles","Food Container",
        "Spoon","Baby Wipes","Baby Shampoo","Baby Soap","Lotion","Antiseptic Syrup","Toys",
        "SunGlasses","Shoes","Slipper","Teether","Baby BackPack","Prescribed Medicine"};
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE,data);
    }

    public List<Items> getTechonologyData() {
        String []data = {"Mobile Phone","Phone Cover","Camera","Speaker","Headphone","Laptop","Power Bank",
        "SD card","Extension Cable"};
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE,data);
    }
    public List<Items> getFoodData() {
        String []data = {"Snacks","Tea Bags","Coffee","Water","Juice","Chips","Baby Food"};
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE,data);
    }
    public List<Items> getCarData() {
        String []data = {"Car Jack","Spare Car Key","Pump","Car Cover","Driving License",
                "Window Sun Shades","Car Document"};
        return prepareItemsList(MyConstants.CAR_ESSENTIALS_CAMEL_CASE,data);
    }
    public List<Items> getNeedsData() {
        String []data = {"BagPack","Daily Bag","Sewing Kit","Travel Lock","Magazine","Important Number"};
        return prepareItemsList(MyConstants.NEEDS_CAMEL_CASE,data);
    }
    public List<Items> getBeachSuppliesData() {
        String []data = {"SunGlasses","Suntan Creme","Beach Umbrella","Beach Slipper",
        "Beach Towel","Beach Bag","Swim Suit"};
        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE,data);
    }
    public List<Items> prepareItemsList(String category, String[]data) {
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();

        for (int i=0; i<list.size(); i++) {
            dataList.add(new Items(list.get(i), category, false));
        }
        return dataList;
    }

    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getBeautyData());
        listOfAllItems.add(getToiletriesData());
        listOfAllItems.add(getMedicineData());
        listOfAllItems.add(getMedicineData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getTechonologyData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getCarData());
        listOfAllItems.add(getNeedsData());
        listOfAllItems.add(getBeachSuppliesData());
        return listOfAllItems;
    }

    public void persistAllData(){
        List<List<Items>> listOfAllItems = getAllData();
        for (List<Items> list: listOfAllItems) {
            for (Items items: list) {
                database.mainDao().saveItem(items);
            }
        }
        System.out.println("Data added.");
    }

    public void persistDataByCategory(String category, Boolean onlyDelete) {
        try {
            List<Items> list = deleteAndGetListByCategory(category, onlyDelete);
            if (!onlyDelete) {
                for(Items item : list) {
                    database.mainDao().saveItem(item);
                }
                Toast.makeText(context, "Reset Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Reset Successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Items> deleteAndGetListByCategory(String category, Boolean onlyDelete) {
        if (onlyDelete) {
            database.mainDao().deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL);
        } else {
            database.mainDao().deleteAllByCategory(category);
        }

        switch (category) {
            case MyConstants.ESSENTIALS_CAMEL_CASE:
                return getBasicData();

            case MyConstants.CLOTHING_CAMEL_CASE:
                return getBasicData();

            case MyConstants.BEAUTY_CAMEL_CASE:
                return getBasicData();

            case MyConstants.TOILETRIES_CAMEL_CASE:
                return getBasicData();

            case MyConstants.MEDICINE_CAMEL_CASE:
                return getBasicData();

            case MyConstants.BABY_NEEDS_CAMEL_CASE:
                return getBasicData();

            case MyConstants.TECHNOLOGY_CAMEL_CASE:
                return getBasicData();

            case MyConstants.FOOD_CAMEL_CASE:
                return getBasicData();

            case MyConstants.CAR_ESSENTIALS_CAMEL_CASE:
                return getBasicData();

            case MyConstants.NEEDS_CAMEL_CASE:
                return getBasicData();

            case MyConstants.BEACH_SUPPLIES_CAMEL_CASE:
                return getBasicData();

            default:
                return new ArrayList<>();
        }
    }
}
