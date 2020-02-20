/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChordManagement;

import ConstantDefinition.ConstantsDefinition;
import DataDefinition.Chord;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gasto_000
 */
public abstract class GenericChordRule {

    /**
     *aplica una regla y devuelve la nueva lista de acordes, se pide la lista entera de acordes por que la regla puede tener condiciones sobre la continuidad.
     * @param base  Lista de acordes que contienen la base en la cual se aplica la regla.
     * @param tempo
     * 
     * @return
     */
  
    public List<Chord> ApplyRule(List<Chord> base, int tempo){return this.seekChordPlace(base, tempo);} // aplica una regla y devuelve la nueva lista de acordes
    // se pide la lista entera de acordes por que la regla puede tener condiciones sobre la continuidad.
    protected List<Chord> seekChordPlace(List<Chord> base, int tempo){
     int twelveBarsFigure = ConstantsDefinition.getInstance().GetBlackFigure()*16*3;
     if(tempo >= twelveBarsFigure-1)//32*3) // 32 equivale a 16 negras, por ende 4 compases, multiplicado por 3 son los 12 compases del blues, si excede este numero  es por que excede los 12 compases.
        return base; // devuelvo la base sin modificaciones.
    List<Chord> modifiedList = new ArrayList<>();
    
    int sumaTiempos=0;
    for(int i =0; i<base.size();i++){ // se puede cambiar todo esto utilizando el agregar en posicion de List.
        if(sumaTiempos == tempo && base.get(i).GetDuration() >1 ) {
            
            List<Chord> acordeConRegla = this.ApplyThisRule(base.get(i)); 
            if(acordeConRegla.size()>0)
                for (int l = 0; l < acordeConRegla.size(); l++) {
                    modifiedList.add(acordeConRegla.get(l));
                }
            else modifiedList.add(base.get(i));
            
            /*modifiedList.add(acordeConRegla.get(0));
                        modifiedList.add(acordeConRegla.get(1));*/
        }else{
            modifiedList.add(base.get(i));
        }
        sumaTiempos += base.get(i).GetDuration();
    }
    return modifiedList;     
    }
    
  
    protected abstract List<Chord> ApplyThisRule(Chord c);
    
   
}
