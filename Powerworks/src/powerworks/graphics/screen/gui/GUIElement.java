package powerworks.graphics.screen.gui;

import powerworks.graphics.ClickableScreenObject;

public abstract class GUIElement extends ClickableScreenObject{
    
    /**
     * Higher layer number means on top
     */
    int layer;
    GUI parent;
    
    GUIElement(GUI parent, int xPixel, int yPixel, int widthPixels, int heightPixels, int layer) {
	super(xPixel + parent.getXPixel(), yPixel + parent.getYPixel(), widthPixels, heightPixels);
	parent.elements.add(this);
	this.parent = parent;
	this.layer = layer;
    }
    
    public abstract void render();
    
    public void update() {}
    
}
