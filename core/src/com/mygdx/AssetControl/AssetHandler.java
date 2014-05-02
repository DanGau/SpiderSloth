package com.mygdx.AssetControl;

import java.io.File;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class AssetHandler {
		
		public static HashMap<String, Texture> loadTextures(String folderName)
		{
			HashMap<String, Texture> result = new HashMap<String, Texture>();
			
			FileHandle folder;
			
			if(Gdx.app.getType() == ApplicationType.Android)
				folder = Gdx.files.internal(folderName);
			else
				folder = Gdx.files.internal("./bin/" + folderName);
			
			FileHandle[] fileList = folder.list();
			
			System.out.println("size: " + fileList.length);
			
			for(int i = 0; i < fileList.length; i++)
			{
				String name = fileList[i].toString().substring(fileList[i].toString().lastIndexOf("/") + 1, fileList[i].toString().lastIndexOf("."));	
				result.put(name, new Texture(fileList[i]));
				System.out.println(name);
			}
			
			return result;			
		}
}
