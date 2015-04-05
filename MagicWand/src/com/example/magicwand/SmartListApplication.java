package com.example.magicwand;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import controller.BuyList;
import controller.DBController;

import android.app.Application;

public class SmartListApplication extends Application{
	final static String DATABASE_NAME = "ProductsDB";
	final static String DATABASE_FILE_NAME = "mykitchendb";
	public DBController db;
	public BuyList buyList;

	@Override public void onCreate(){
    
        super.onCreate();
    	// DataBase Pre-Load
		String destDir = "/data/data/" + getPackageName() + "/databases/";
		String destPath = destDir + DATABASE_NAME;
		File f = new File(destPath);
		//if (!f.exists())
		/*{
			// ---make sure directory exists---
			File directory = new File(destDir);
			directory.mkdirs();
			// ---copy the db from the assets folder into
			// the databases folder---
			try {
				copyDB(getBaseContext().getAssets().open(DATABASE_FILE_NAME),
						new FileOutputStream(destPath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		db = new DBController(this);
		buyList = new BuyList();

	
		 
		 	 
    }
	private void copyDB(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		// ---copy 1K bytes at a time---
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}
}

