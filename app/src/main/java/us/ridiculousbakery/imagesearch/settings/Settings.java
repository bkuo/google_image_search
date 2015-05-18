package us.ridiculousbakery.imagesearch.settings;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by bkuo on 5/18/15.
 */
public class Settings implements Serializable{
    public Integer get_selectedSize() {
        if(_selectedSize ==null) _selectedSize=0;
        return _selectedSize;
    }

    public Integer get_selectedColor() {
        if(_selectedColor ==null) _selectedColor=0;
        return _selectedColor;
    }

    public Integer get_selectedType() {
        if(_selectedType ==null) _selectedType=0;
        return _selectedType;
    }

    public String get_selectedSite() {
        if(_selectedSite ==null) _selectedSite="";
        return _selectedSite;
    }

    public Integer _selectedSize;
    public Integer _selectedColor;
    public Integer _selectedType;
    public String _selectedSite;
    public Settings(){}
    
    public static Settings fromBundle(Bundle b){
        return (Settings) b.getSerializable("data");
    }

    public Bundle toBundle(){
        Bundle b = new Bundle();
        b.putSerializable("data", this);
        return b;
    }
}
