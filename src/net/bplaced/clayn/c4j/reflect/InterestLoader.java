package net.bplaced.clayn.c4j.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.bplaced.clayn.c4j.Configuration;
import net.bplaced.clayn.c4j.Setting;
import net.bplaced.clayn.c4j.util.ConfigUtil;
import net.bplaced.clayn.c4j.util.Pair;

/**
 * @deprecated Class no longer needed for its initial purposes.
 *
 * @author Clayn
 */
public class InterestLoader
{

    //<editor-fold desc="Attribute">
    private Configuration config;

    //</editor-fold>
    //<editor-fold desc="Konstruktoren">
    public InterestLoader(Configuration config)
    {
        this.config = config;
    }

    //</editor-fold>
    //<editor-fold desc="Public">
    public Configuration getConfig()
    {
        return config;
    }

    public void setConfig(Configuration config)
    {
        this.config = config;
    }
    public Pair<Setting,String>[] load(Interester inte)
    {
        List<Pair<Setting,String>> back=new ArrayList<>();
        for(Pair<Setting,String> pair:inte.getInterestedKeys())
        {
            Setting key=pair.getFirst();
            String field=pair.getSecond();
            Class cl=inte.getClass();
            Object val=config.get(key);
            try
            {
                Field fl=cl.getDeclaredField(field);
                ConfigUtil.setField(fl, inte, val);
                
            }
            catch (NoSuchFieldException | SecurityException ex)
            {
                Logger.getLogger(InterestLoader.class.getName()).
                        log(Level.WARNING, null, ex);
                back.add(pair);
            }
            catch (IllegalArgumentException | IllegalAccessException ex)
            {
                Logger.getLogger(InterestLoader.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
        return back.toArray(new Pair[back.size()]);
    }
    //</editor-fold>
    //<editor-fold desc="Private">
    //</editor-fold>

}
