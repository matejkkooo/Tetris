/*
OvladanieHry.java
*/
public class OvladanieHry {
    private boolean stavVlavo;
    private boolean stavVpravo;
    private boolean stavOtocenie;
    private boolean stavDrop;


    public OvladanieHry() {
        stavVlavo = stavVpravo = stavOtocenie = stavDrop = false;
    }

    public void stlacVlavo() { stavVlavo = true; }
    public void pustVlavo() { stavVlavo = false; }
    public void stlacVpravo() { stavVpravo = true; }
    public void pustVpravo() { stavVpravo = false; }
    public void stlacOtocenie() { stavOtocenie = true; }
    public void pustOtocenie() { stavOtocenie = false; }
    public void stlacDrop() { stavDrop = true; }
    public void pustDrop() { stavDrop = false; }

    public boolean isVavo() { return stavVlavo; }
    public boolean isVpravo() { return stavVpravo; }
    public boolean isOtocenie() { return stavOtocenie; }
    public boolean isDrop() { return stavDrop; }


}
