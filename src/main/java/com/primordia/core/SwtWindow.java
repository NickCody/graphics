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
    final Shell shell;
    GLCapabilities swtCapabilities;
    Callback swtDebugProc;
    GLCanvas swtCanvas;
    WindowParams windowParams;

    public SwtWindow(Display display, WindowParams windowParams) {
        this.windowParams = windowParams;

        shell = new Shell(display);
        shell.setText(windowParams.getTitle());
        shell.setLayout(new FillLayout());

        shell.addListener(SWT.Traverse, new Listener() {
            public void handleEvent(Event event) {
                switch (event.detail) {
                    case SWT.TRAVERSE_ESCAPE:
                        shell.close();
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
        swtCanvas = new GLCanvas(shell, SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE, data);
        int dw = shell.getSize().x - shell.getClientArea().width;
        int dh = shell.getSize().y - shell.getClientArea().height;
        shell.setSize(windowParams.getWidth() + dw, windowParams.getHeight() + dh);
        swtCanvas.setCurrent();
        swtCapabilities = createCapabilities();
        swtDebugProc = GLUtil.setupDebugMessageCallback();
    }

    public void open() {
        shell.open();
    }

    public void onRender() {
        if (!shell.isDisposed()) {
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
        // Dispose of SWT
        if (swtDebugProc != null)
            swtDebugProc.free();
        if (!shell.isDisposed())
            shell.dispose();
    }
}
