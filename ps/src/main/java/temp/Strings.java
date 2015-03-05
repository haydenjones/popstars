/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp;

/**
 *
 * @author hjones
 */
public class Strings {
    private Strings() {
        super();
    }
    
    public static String trim(Object object) {
        return (object == null) ? "" : object.toString().trim();
    }
    
}
