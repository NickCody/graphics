package com.primordia.model;

public class Color {
    private Float r;
    private Float g;
    private Float b;
    private Float a;

    public Color(Float r, Float g, Float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0f;
    }

    public Float getRed() {
        return r;
    }

    public void setRed(Float r) {
        this.r = r;
    }

    public Float getGreen() {
        return g;
    }

    public void setGreen(Float g) {
        this.g = g;
    }

    public Float getBlue() {
        return b;
    }

    public void setBlue(Float b) {
        this.b = b;
    }

    public Float getAlpha() {
        return a;
    }

    public void setAlpha(Float a) {
        this.a = a;
    }
    static Color fromWeb(Integer r, Integer g, Integer b) {
        return new Color((float)r/255f, (float)g/255f, (float)b/255f);
    }

    public static Color Black = fromWeb(0,0,0);
    public static Color White = fromWeb(255,255,255);
    public static Color Red = fromWeb(255,0,0);
    public static Color Lime = fromWeb(0,255,0);
    public static Color Blue = fromWeb(0,0,255);
    public static Color Yellow = fromWeb(255,255,0);
    public static Color Cyan = fromWeb(0,255,255);
    public static Color Fuchsia = fromWeb(255,0,255);
    public static Color Silver = fromWeb(192,192,192);
    public static Color Gray = fromWeb(128,128,128);
    public static Color Maroon = fromWeb(128,0,0);
    public static Color Olive = fromWeb(128,128,0);
    public static Color Green = fromWeb(0,128,0);
    public static Color Purple = fromWeb(128,0,128);
    public static Color Teal = fromWeb(0,128,128);
    public static Color Navy = fromWeb(0,0,128);
    public static Color DarkRed = fromWeb(139,0,0);
    public static Color Brown = fromWeb(165,42,42);
    public static Color Firebrick = fromWeb(178,34,34);
    public static Color Crimson = fromWeb(220,20,60);
    public static Color Tomato = fromWeb(255,99,71);
    public static Color Coral = fromWeb(255,127,80);
    public static Color IndianRed = fromWeb(205,92,92);
    public static Color LightCoral = fromWeb(240,128,128);
    public static Color DarkSalmon = fromWeb(233,150,122);
    public static Color Salmon = fromWeb(250,128,114);
    public static Color LightSalmon = fromWeb(255,160,122);
    public static Color OrangeRed = fromWeb(255,69,0);
    public static Color DarkOrange = fromWeb(255,140,0);
    public static Color Orange = fromWeb(255,165,0);
    public static Color Gold = fromWeb(255,215,0);
    public static Color DarkGoldenRod = fromWeb(184,134,11);
    public static Color GoldenRod = fromWeb(218,165,32);
    public static Color PaleGoldenRod = fromWeb(238,232,170);
    public static Color DarkKhaki = fromWeb(189,183,107);
    public static Color Khaki = fromWeb(240,230,140);
    public static Color YellowGreen = fromWeb(154,205,50);
    public static Color DarkOliveGreen = fromWeb(85,107,47);
    public static Color OliveDrab = fromWeb(107,142,35);
    public static Color LawnGreen = fromWeb(124,252,0);
    public static Color Chartreuse = fromWeb(127,255,0);
    public static Color GreenYellow = fromWeb(173,255,47);
    public static Color DarkGreen = fromWeb(0,100,0);
    public static Color ForestGreen = fromWeb(34,139,34);
    public static Color LimeHreen = fromWeb(50,205,50);
    public static Color LightGreen = fromWeb(144,238,144);
    public static Color PaleGreen = fromWeb(152,251,152);
    public static Color DarkSeaGreen = fromWeb(143,188,143);
    public static Color MediumSpringGreen = fromWeb(0,250,154);
    public static Color SpringGreen = fromWeb(0,255,127);
    public static Color SeaGreen = fromWeb(46,139,87);
    public static Color MediumAquaMarine = fromWeb(102,205,170);
    public static Color MediumSeaGreen = fromWeb(60,179,113);
    public static Color LightSeaGreen = fromWeb(32,178,170);
    public static Color DarkSlateGray = fromWeb(47,79,79);
    public static Color DarkCyan = fromWeb(0,139,139);
    public static Color Aqua = fromWeb(0,255,255);
    public static Color LightCyan = fromWeb(224,255,255);
    public static Color DarkTurquoise = fromWeb(0,206,209);
    public static Color Turquoise = fromWeb(64,224,208);
    public static Color MediumTurquoise = fromWeb(72,209,204);
    public static Color PaleTurquoise = fromWeb(175,238,238);
    public static Color AquaMarine = fromWeb(127,255,212);
    public static Color PowderBlue = fromWeb(176,224,230);
    public static Color CadetBlue = fromWeb(95,158,160);
    public static Color SteelBlue = fromWeb(70,130,180);
    public static Color CornFlowerBlue = fromWeb(100,149,237);
    public static Color DeepSkyBlue = fromWeb(0,191,255);
    public static Color DodgerBlue = fromWeb(30,144,255);
    public static Color LightBlue = fromWeb(173,216,230);
    public static Color SkyBlue = fromWeb(135,206,235);
    public static Color LightSkyBlue = fromWeb(135,206,250);
    public static Color MidnightBlue = fromWeb(25,25,112);
    public static Color DarkBlue = fromWeb(0,0,139);
    public static Color MediumBlue = fromWeb(0,0,205);
    public static Color RoyalBlue = fromWeb(65,105,225);
    public static Color BlueViolet = fromWeb(138,43,226);
    public static Color Indigo = fromWeb(75,0,130);
    public static Color DarkSlateBlue = fromWeb(72,61,139);
    public static Color SlateBlue = fromWeb(106,90,205);
    public static Color MediumSlateBlue = fromWeb(123,104,238);
    public static Color MediumPurple = fromWeb(147,112,219);
    public static Color DarkMagenta = fromWeb(139,0,139);
    public static Color DarkViolet = fromWeb(148,0,211);
    public static Color DarkOrchid = fromWeb(153,50,204);
    public static Color MediumOrchid = fromWeb(186,85,211);
    public static Color Thistle = fromWeb(216,191,216);
    public static Color Plum = fromWeb(221,160,221);
    public static Color Violet = fromWeb(238,130,238);
    public static Color Orchid = fromWeb(218,112,214);
    public static Color MediumVioletRed = fromWeb(199,21,133);
    public static Color PaleVioletRed = fromWeb(219,112,147);
    public static Color DeepPink = fromWeb(255,20,147);
    public static Color HotPink = fromWeb(255,105,180);
    public static Color LightPink = fromWeb(255,182,193);
    public static Color Pink = fromWeb(255,192,203);
    public static Color AntiqueWhite = fromWeb(250,235,215);
    public static Color Beige = fromWeb(245,245,220);
    public static Color Bisque = fromWeb(255,228,196);
    public static Color BlanchedAlmond = fromWeb(255,235,205);
    public static Color Wheat = fromWeb(245,222,179);
    public static Color CornSilk = fromWeb(255,248,220);
    public static Color LemonChiffon = fromWeb(255,250,205);
    public static Color LightGoldenRodYellow = fromWeb(250,250,210);
    public static Color LightYellow = fromWeb(255,255,224);
    public static Color SaddleBrown = fromWeb(139,69,19);
    public static Color Sienna = fromWeb(160,82,45);
    public static Color Chocolate = fromWeb(210,105,30);
    public static Color Peru = fromWeb(205,133,63);
    public static Color SandyBrown = fromWeb(244,164,96);
    public static Color BurlyWood = fromWeb(222,184,135);
    public static Color Tan = fromWeb(210,180,140);
    public static Color RosyBrown = fromWeb(188,143,143);
    public static Color Moccasin = fromWeb(255,228,181);
    public static Color NavajoWhite = fromWeb(255,222,173);
    public static Color PeachPuff = fromWeb(255,218,185);
    public static Color MistyRose = fromWeb(255,228,225);
    public static Color LavenderBlush = fromWeb(255,240,245);
    public static Color Linen = fromWeb(250,240,230);
    public static Color OldLace = fromWeb(253,245,230);
    public static Color PapayaWhip = fromWeb(255,239,213);
    public static Color SeaShell = fromWeb(255,245,238);
    public static Color MintCream = fromWeb(245,255,250);
    public static Color SlateGray = fromWeb(112,128,144);
    public static Color LightSlateGray = fromWeb(119,136,153);
    public static Color LightSteelBlue = fromWeb(176,196,222);
    public static Color Lavender = fromWeb(230,230,250);
    public static Color FloralWhite = fromWeb(255,250,240);
    public static Color AliceBlue = fromWeb(240,248,255);
    public static Color GhostWhite = fromWeb(248,248,255);
    public static Color Honeydew = fromWeb(240,255,240);
    public static Color Ivory = fromWeb(255,255,240);
    public static Color Azure = fromWeb(240,255,255);
    public static Color Snow = fromWeb(255,250,250);
    public static Color DimGray = fromWeb(105,105,105);
    public static Color DarkGray = fromWeb(169,169,169);
    public static Color LightGray = fromWeb(211,211,211);
    public static Color Gainsboro = fromWeb(220,220,220);
    public static Color WhiteSmoke = fromWeb(245,245,245);


}