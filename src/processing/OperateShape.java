package processing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import processing.core.PApplet;

public abstract class OperateShape extends MoveShape implements MouseMotionListener, MouseListener {
	public OperateShape(PApplet papplet) {
		super(papplet);
		papplet.addMouseListener(this);
		papplet.addMouseMotionListener(this);
	}
	
	// フレー�?��ートをカウントす�?
	private int mousePressedFps;
	// マウスが移動した間のフレー�?��ート数
	private int mouseReleasedFps;
	// マウスが押されたとき�?X座�? Y座�?
	private float mousePressedX, mousePressedY;
	// マウスが離れたとき�?X座�? Y座�?
	private float mouseReleasedX, mouseReleasedY;
	// マウスが押されたとき�?点と離れたとき�?点を結ん�?���?なす�?
	private float mouseAngle;
	// マウスが移動した距離
	private float mouseDistance;
	
	// -------------PApplet MouseListener----------------
	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mousePressedFps = papplet.frameCount;
		mousePressedX = e.getX();
		mousePressedY = e.getY();
		
		if (this.getMouseInShape()) {
			this.setX(e.getX());
			this.setY(e.getY());
			this.setSpeed(0);
			this.setClickedShapeFlag(1);
		}
	}

	public void mouseReleased(MouseEvent e) {
		mouseReleasedFps = papplet.frameCount;
		
		if (this.getClickedShapeFlag() == 1) {
			mouseReleasedX = e.getX();
			mouseReleasedY = e.getY();
			mouseDistance = PApplet.dist(mousePressedX, mousePressedY, mouseReleasedX, mouseReleasedY);
			mouseAngle = PApplet.degrees(PApplet.atan((mousePressedY - mouseReleasedY) / (mousePressedX - mouseReleasedX)));

			if ((mousePressedX - mouseReleasedX) >= 0 && (mousePressedY - mouseReleasedY) <= 0) {
				mouseAngle += 180;
			} else if ((mousePressedX - mouseReleasedX) >= 0 && (mousePressedY - mouseReleasedY) >= 0) {
				mouseAngle -= 180;
			}

			this.setSpeed(mouseDistance / (mouseReleasedFps - mousePressedFps));
			this.setAngle(mouseAngle);
			this.setClickedShapeFlag(0);
		}
	}

	public void mouseEntered(MouseEvent e) {
	}
	
	public void mouseExited(MouseEvent e) {
	}
	// ---------------------------------------------------
	
	// ---------- PApplet MouseMotionListener -------
	public void mouseDragged(MouseEvent e) {
		if (this.getClickedShapeFlag() == 1) {
			this.setX(e.getX());
			this.setY(e.getY());
		}
	}

	public void mouseMoved(MouseEvent e) {
	}
	// ---------------------------------------------------
	
	public abstract boolean getMouseInShape(); 
}