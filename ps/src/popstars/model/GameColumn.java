/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package popstars.model;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hjones
 */
public class GameColumn {
    static final ConcurrentHashMap<String, GameColumn> CACHE = new ConcurrentHashMap<>();
    
    static GameColumn getInstance(byte[] bytes) {
        GameColumn gc = new GameColumn(bytes);
        String key = gc.toString();
        GameColumn cache = CACHE.putIfAbsent(key, gc);
        if (cache != null) {
            return cache;
        }
        return gc;
    }

    static final GameColumn EMPTY_COLUMN = GameColumn.getInstance(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
            
    private final byte[] bytes;
    
    private GameColumn(byte[] newBytes) {
        super();
        bytes = newBytes;
    }

    public byte[] toByteArray() {
        byte[] array = new byte[bytes.length];
        System.arraycopy(bytes, 0, array, 0, bytes.length);
        return array;
    }

    GameColumn removePiece(int row) {
        byte[] newBytes = new byte[bytes.length];
        // 0 is the top
        // We are remove the (row) item
        
        // Let's keep the bottom
        System.arraycopy(bytes, row + 1, newBytes, row + 1, (9 - row));
        System.arraycopy(bytes, 0, newBytes, 1, row);
        newBytes[0] = 0;
        
        return getInstance(newBytes);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Byte.valueOf(b));
        }
        return sb.toString();
    }
}
