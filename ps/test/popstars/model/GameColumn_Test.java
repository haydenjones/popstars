/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package popstars.model;

import org.junit.Test;

/**
 *
 * @author hjones
 */
public class GameColumn_Test {
    
    @Test
    public void works() {
        GameColumn column = GameColumn.getInstance(new byte[] { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5});
        
        for (int i1=0; i1<10; i1++) {
            System.out.println("");
            System.out.println(column);
            System.out.println("Remove piece " + i1);
            
            GameColumn actual = column.removePiece(i1);
            System.out.println(actual);
        }

        
    }
}
