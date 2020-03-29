package com.primordia.core;

import com.primordia.model.WindowParams;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL.setCapabilities;
import static org.lwjgl.opengl.GL11.*;

public class SwtWindow {
    GLCapabilities swtCapabilities;
    GLCanvas swtCanvas;
    WindowParams windowParams;
    final Shell swtShell;

    public SwtWindow(App app, WindowParams windowParams) {
        this.windowParams = windowParams;
        swtShell = new Shell(app.getDisplay());
        swtShell.setText(windowParams.getTitle());
        swtShell.setLayout(new FillLayout());

        swtShell.addListener(SWT.Traverse, new Listener() {
            public void handleEvent(Event event) {
                switch (event.detail) {
                    case SWT.TRAVERSE_ESCAPE:
                        swtShell.close();
                        event.detail = SWT.TRAVERSE_NONE;
                        event.doit = false;
                        break;
                    default:
                        break;
                }
            }
        });

        GLData data = new GLData();
        data.doubleBuffer = true;
        swtCanvas = new GLCanvas(swtShell, SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE, data);
        int dw = swtShell.getSize().x - swtShell.getClientArea().width;
        int dh = swtShell.getSize().y - swtShell.getClientArea().height;
        swtShell.setSize(windowParams.getWidth() + dw, windowParams.getHeight() + dh);
        swtCapabilities = createCapabilities();
    }

    public void open() {
        swtShell.open();
    }

    public void onRender() {
        if (!swtShell.isDisposed()) {
            // Render to SWT window
            if (!swtCanvas.isDisposed()) {
                swtCanvas.setCurrent();
                setCapabilities(swtCapabilities);
                glClearColor(windowParams.getBackgroundColor().getRed(), windowParams.getBackgroundColor().getGreen(), windowParams.getBackgroundColor().getBlue(), 1.0f);
                glClear(GL_COLOR_BUFFER_BIT);
                swtCanvas.swapBuffers();
            }
        }
    }

    public void close() {
        if (!swtShell.isDisposed())
            swtShell.dispose();
    }
}
