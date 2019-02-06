package com.company.Logic;

import com.company.Pieces.Bishop;
import com.company.Pieces.Knight;
import com.company.Pieces.Queen;
import com.company.Pieces.Rook;

import java.util.Dictionary;
import java.util.Hashtable;

public class Global {
    public static final int LENGTH = 8;

    public static final Dictionary<String, Class> promoteFactory;

    static {
        promoteFactory = new Hashtable<>();
        promoteFactory.put("B", Bishop.class);
        promoteFactory.put("Kn", Knight.class);
        promoteFactory.put("R", Rook.class);
        promoteFactory.put("Q", Queen.class);
    }
}
