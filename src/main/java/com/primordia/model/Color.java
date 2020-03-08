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

    public static Color Reds_lightsalmon = fromWeb(255,160,122);
    public static Color Reds_salmon = fromWeb(250,128,114);
    public static Color Reds_darksalmon = fromWeb(233,150,122);
    public static Color Reds_lightcoral = fromWeb(240,128,128);
    public static Color Reds_indianred = fromWeb(205,92,92);
    public static Color Reds_crimson = fromWeb(220,20,60);
    public static Color Reds_firebrick = fromWeb(178,34,34);
    public static Color Reds_red = fromWeb(255,0,0);
    public static Color Reds_darkred = fromWeb(139,0,0);
    public static Color Oranges_coral = fromWeb(255,127,80);
    public static Color Oranges_tomato = fromWeb(255,99,71);
    public static Color Oranges_orangered = fromWeb(255,69,0);
    public static Color Oranges_gold = fromWeb(255,215,0);
    public static Color Oranges_orange = fromWeb(255,165,0);
    public static Color Oranges_darkorange = fromWeb(255,140,0);
    public static Color Yellows_lightyellow = fromWeb(255,255,224);
    public static Color Yellows_lemonchiffon = fromWeb(255,250,205);
    public static Color Yellows_lightgoldenrodyellow = fromWeb(250,250,210);
    public static Color Yellows_papayawhip = fromWeb(255,239,213);
    public static Color Yellows_moccasin = fromWeb(255,228,181);
    public static Color Yellows_peachpuff = fromWeb(255,218,185);
    public static Color Yellows_palegoldenrod = fromWeb(238,232,170);
    public static Color Yellows_khaki = fromWeb(240,230,140);
    public static Color Yellows_darkkhaki = fromWeb(189,183,107);
    public static Color Yellows_yellow = fromWeb(255,255,0);
    public static Color Greens_lawngreen = fromWeb(124,252,0);
    public static Color Greens_chartreuse = fromWeb(127,255,0);
    public static Color Greens_limegreen = fromWeb(50,205,50);
    public static Color Greens_lime = fromWeb(0,255,0);
    public static Color Greens_forestgreen = fromWeb(34,139,34);
    public static Color Greens_green = fromWeb(0,128,0);
    public static Color Greens_darkgreen = fromWeb(0,100,0);
    public static Color Greens_greenyellow = fromWeb(173,255,47);
    public static Color Greens_yellowgreen = fromWeb(154,205,50);
    public static Color Greens_springgreen = fromWeb(0,255,127);
    public static Color Greens_mediumspringgreen = fromWeb(0,250,154);
    public static Color Greens_lightgreen = fromWeb(144,238,144);
    public static Color Greens_palegreen = fromWeb(152,251,152);
    public static Color Greens_darkseagreen = fromWeb(143,188,143);
    public static Color Greens_mediumseagreen = fromWeb(60,179,113);
    public static Color Greens_seagreen = fromWeb(46,139,87);
    public static Color Greens_olive = fromWeb(128,128,0);
    public static Color Greens_darkolivegreen = fromWeb(85,107,47);
    public static Color Greens_olivedrab = fromWeb(107,142,35);
    public static Color Cyans_lightcyan = fromWeb(224,255,255);
    public static Color Cyans_cyan = fromWeb(0,255,255);
    public static Color Cyans_aqua = fromWeb(0,255,255);
    public static Color Cyans_aquamarine = fromWeb(127,255,212);
    public static Color Cyans_mediumaquamarine = fromWeb(102,205,170);
    public static Color Cyans_paleturquoise = fromWeb(175,238,238);
    public static Color Cyans_turquoise = fromWeb(64,224,208);
    public static Color Cyans_mediumturquoise = fromWeb(72,209,204);
    public static Color Cyans_darkturquoise = fromWeb(0,206,209);
    public static Color Cyans_lightseagreen = fromWeb(32,178,170);
    public static Color Cyans_cadetblue = fromWeb(95,158,160);
    public static Color Cyans_darkcyan = fromWeb(0,139,139);
    public static Color Cyans_teal = fromWeb(0,128,128);
    public static Color Blues_powderblue = fromWeb(176,224,230);
    public static Color Blues_lightblue = fromWeb(173,216,230);
    public static Color Blues_lightskyblue = fromWeb(135,206,250);
    public static Color Blues_skyblue = fromWeb(135,206,235);
    public static Color Blues_deepskyblue = fromWeb(0,191,255);
    public static Color Blues_lightsteelblue = fromWeb(176,196,222);
    public static Color Blues_dodgerblue = fromWeb(30,144,255);
    public static Color Blues_cornflowerblue = fromWeb(100,149,237);
    public static Color Blues_steelblue = fromWeb(70,130,180);
    public static Color Blues_royalblue = fromWeb(65,105,225);
    public static Color Blues_blue = fromWeb(0,0,255);
    public static Color Blues_mediumblue = fromWeb(0,0,205);
    public static Color Blues_darkblue = fromWeb(0,0,139);
    public static Color Blues_navy = fromWeb(0,0,128);
    public static Color Blues_midnightblue = fromWeb(25,25,112);
    public static Color Blues_mediumslateblue = fromWeb(123,104,238);
    public static Color Blues_slateblue = fromWeb(106,90,205);
    public static Color Blues_darkslateblue = fromWeb(72,61,139);
    public static Color Purples_lavender = fromWeb(230,230,250);
    public static Color Purples_thistle = fromWeb(216,191,216);
    public static Color Purples_plum = fromWeb(221,160,221);
    public static Color Purples_violet = fromWeb(238,130,238);
    public static Color Purples_orchid = fromWeb(218,112,214);
    public static Color Purples_fuchsia = fromWeb(255,0,255);
    public static Color Purples_magenta = fromWeb(255,0,255);
    public static Color Purples_mediumorchid = fromWeb(186,85,211);
    public static Color Purples_mediumpurple = fromWeb(147,112,219);
    public static Color Purples_blueviolet = fromWeb(138,43,226);
    public static Color Purples_darkviolet = fromWeb(148,0,211);
    public static Color Purples_darkorchid = fromWeb(153,50,204);
    public static Color Purples_darkmagenta = fromWeb(139,0,139);
    public static Color Purples_purple = fromWeb(128,0,128);
    public static Color Purples_indigo = fromWeb(75,0,130);
    public static Color Pinks_pink = fromWeb(255,192,203);
    public static Color Pinks_lightpink = fromWeb(255,182,193);
    public static Color Pinks_hotpink = fromWeb(255,105,180);
    public static Color Pinks_deeppink = fromWeb(255,20,147);
    public static Color Pinks_palevioletred = fromWeb(219,112,147);
    public static Color Pinks_mediumvioletred = fromWeb(199,21,133);
    public static Color Whites_white = fromWeb(255,255,255);
    public static Color Whites_snow = fromWeb(255,250,250);
    public static Color Whites_honeydew = fromWeb(240,255,240);
    public static Color Whites_mintcream = fromWeb(245,255,250);
    public static Color Whites_azure = fromWeb(240,255,255);
    public static Color Whites_aliceblue = fromWeb(240,248,255);
    public static Color Whites_ghostwhite = fromWeb(248,248,255);
    public static Color Whites_whitesmoke = fromWeb(245,245,245);
    public static Color Whites_seashell = fromWeb(255,245,238);
    public static Color Whites_beige = fromWeb(245,245,220);
    public static Color Whites_oldlace = fromWeb(253,245,230);
    public static Color Whites_floralwhite = fromWeb(255,250,240);
    public static Color Whites_ivory = fromWeb(255,255,240);
    public static Color Whites_antiquewhite = fromWeb(250,235,215);
    public static Color Whites_linen = fromWeb(250,240,230);
    public static Color Whites_lavenderblush = fromWeb(255,240,245);
    public static Color Whites_mistyrose = fromWeb(255,228,225);
    public static Color Grays_gainsboro = fromWeb(220,220,220);
    public static Color Grays_lightgray = fromWeb(211,211,211);
    public static Color Grays_silver = fromWeb(192,192,192);
    public static Color Grays_darkgray = fromWeb(169,169,169);
    public static Color Grays_gray = fromWeb(128,128,128);
    public static Color Grays_dimgray = fromWeb(105,105,105);
    public static Color Grays_lightslategray = fromWeb(119,136,153);
    public static Color Grays_slategray = fromWeb(112,128,144);
    public static Color Grays_darkslategray = fromWeb(47,79,79);
    public static Color Grays_black = fromWeb(0,0,0);
    public static Color Browns_cornsilk = fromWeb(255,248,220);
    public static Color Browns_blanchedalmond = fromWeb(255,235,205);
    public static Color Browns_bisque = fromWeb(255,228,196);
    public static Color Browns_navajowhite = fromWeb(255,222,173);
    public static Color Browns_wheat = fromWeb(245,222,179);
    public static Color Browns_burlywood = fromWeb(222,184,135);
    public static Color Browns_tan = fromWeb(210,180,140);
    public static Color Browns_rosybrown = fromWeb(188,143,143);
    public static Color Browns_sandybrown = fromWeb(244,164,96);
    public static Color Browns_goldenrod = fromWeb(218,165,32);
    public static Color Browns_peru = fromWeb(205,133,63);
    public static Color Browns_chocolate = fromWeb(210,105,30);
    public static Color Browns_saddlebrown = fromWeb(139,69,19);
    public static Color Browns_sienna = fromWeb(160,82,45);
    public static Color Browns_brown = fromWeb(165,42,42);
    public static Color Browns_maroon = fromWeb(128,0,0);


}