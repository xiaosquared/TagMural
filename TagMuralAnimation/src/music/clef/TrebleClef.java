package music.clef;

import processing.core.PVector;
import processing.core.PApplet;

class TrebleClef {
    BezierCurve curve;
    PVector origin;
    
    
    float MIN_ANGLE = -PApplet.PI/25;
    float MAX_ANGLE = PApplet.PI/5;
    float rot = MIN_ANGLE;
    boolean toRight = true;
    
    public TrebleClef(float x, float y, PApplet parent) {
      curve = new BezierCurve(new BPoint(610, 436, true));
      addSegment(new PVector(570, 435), new PVector(576, 386), new PVector(605, 382));
      addSegment(new PVector(657, 377), new PVector(684, 457), new PVector(620, 478));
      addSegment(new PVector(535, 485), new PVector(525, 398), new PVector(585, 309));
      addSegment(new PVector(613, 251), new PVector(596, 198), new PVector(560, 171));
      addSegment(new PVector(577, 274), new PVector(630, 532), new PVector(640, 581));
      addSegment(new PVector(641, 612), new PVector(609, 620), new PVector(607, 586));
      curve.zeroOrigin();
      this.origin = new PVector(x, y);
      
      curve.viewControlPts(false);
      curve.viewCurve(false);
      curve.scale(0.5f);
      
      curve.fillWithLetters("hello world ", 6, parent);
    }
   
    public TrebleClef(BPoint b) {
      curve = new BezierCurve(b);  
    }
   
    public void setOriginY(float y) { origin.y = y; } 
    
    public void addSegment(PVector c0, PVector c1, PVector a1) {
      curve.addSegment(new BPoint(c0, false), new BPoint(c1, false), new BPoint(a1, true));
    }
    
    public void update() {
      if (toRight) { rot += 0.003; }
      else { rot -= 0.003; }
      if (rot >= MAX_ANGLE || rot <= MIN_ANGLE) {
         toRight = ! toRight; 
      }
        
    }
    
    public void draw(PApplet parent) {
      parent.pushMatrix();
      parent.translate(origin.x, origin.y);
      parent.rotate(rot);
      curve.draw(parent);
      parent.popMatrix();
    }
}
