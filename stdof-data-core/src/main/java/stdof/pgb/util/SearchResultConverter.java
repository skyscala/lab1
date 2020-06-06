/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.pgb.util;

/**
 *
 * @author zlhso
 */
public interface SearchResultConverter<I,O> {
    
    O perform(I i);    
    
}
