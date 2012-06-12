package nl.nhl.idp;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;

/**
 * Settingsmenu that shows the current values; IP adress and Port
 * @author BP de Vries
 *
 */
public class PrefsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	   @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        addPreferencesFromResource(R.xml.prefs);
	        PreferenceManager.setDefaultValues(PrefsActivity.this, R.xml.prefs, false);
	        // Get all the preferences
	        for(int i=0;i<getPreferenceScreen().getPreferenceCount();i++){
	         initSummary(getPreferenceScreen().getPreference(i));
	        }
	    }

	    @Override 
	    protected void onResume(){
	        super.onResume();
	        // Set up a listener whenever a key changes             
	        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	    }

	    @Override 
	    protected void onPause() { 
	        super.onPause();
	        // Unregister the listener whenever a key changes             
	        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);     
	    } 

	    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) { 
	        updatePrefSummary(findPreference(key));
	    }

	    /**
	     * Get all summaries 
	     * @author BP de Vries
	     * 
	     * @param p
	     */
	    private void initSummary(Preference p){
	       if (p instanceof PreferenceCategory){
	            PreferenceCategory pCat = (PreferenceCategory)p;
	            for(int i=0;i<pCat.getPreferenceCount();i++){
	                initSummary(pCat.getPreference(i));
	            }
	        }else{
	            updatePrefSummary(p);
	        }

	    }

	    /**
	     * Update current summaries
	     * @author BP de Vries
	     * 
	     * @param p
	     */
	    private void updatePrefSummary(Preference p){
	        if (p instanceof EditTextPreference) {
	            EditTextPreference editTextPref = (EditTextPreference) p; 
	            p.setSummary("Current value: " + editTextPref.getText()); 
	        }

	    }
}
