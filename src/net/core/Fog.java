package net.core;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import net.minecraft.client.renderer.OpenGlHelper;

public class Fog {
    public int fogMode = -1;
    public float fogDensity = -1;
    public float fogStart = -1;
    public float fogEnd = -1;
    public float[] fogColor = new float[]{-1, -1, -1, -1};
    public boolean fogRadial = false;
    
    public void loadDefaults() {
        fogMode = GL11.GL_EXP;
        fogDensity = 1.0f;
        fogStart = 0.0f;
        fogEnd = 1.0f;
        for (int i = 0; i < 4; i++) {
            fogColor[i] = 0;
        }
        fogRadial = false;
    }

    /**
     * this = program.fog
     * @param program
     * @param fog from GlStateManager
     */
    public void upload(Program program, Fog fog) {
        // OpenGlHelper.glUniform1i(program.fogMode, fog.fogMode);
        // OpenGlHelper.glUniform1i(program.fogRadial, fog.fogRadial ? 1 : 0);
        // GL20.glUniform4f(program.fogColor, fogColor[0], fogColor[1], fogColor[2], fogColor[3]);
        // GL20.glUniform1f(program.fogStart, fog.fogStart);
        // GL20.glUniform1f(program.fogEnd, fog.fogEnd);
        // GL20.glUniform1f(program.fogDensity, fog.fogDensity);
        if (fog.fogMode != this.fogMode) {
            this.fogMode = fog.fogMode;
            if (program.fogMode != -1) {
                OpenGlHelper.glUniform1i(program.fogMode, this.fogMode);
            }
        }

        if (fog.fogRadial != this.fogRadial) {
            this.fogRadial = fog.fogRadial;
            if (program.fogMode != -1) {
                OpenGlHelper.glUniform1i(program.fogRadial, this.fogRadial ? 1 : 0);
            }
        }
        
        if (!eq(fogColor, fog.fogColor)) {
            for (int i = 0; i < 4; i++) {
                this.fogColor[i] = fog.fogColor[i];
            }
            if (program.fogColor != -1) {
                GL20.glUniform4f(program.fogColor, fogColor[0], fogColor[1], fogColor[2], fogColor[3]);
                // GL20.glUniform4f(program.fogColor, 1.0f, 0.0f, 0.0f, 1.0f);
            }
        }

        if (fogMode == GL11.GL_LINEAR) {
            if (fog.fogStart != this.fogStart) {
                this.fogStart = fog.fogStart;
                if (program.fogStart != -1) {
                    GL20.glUniform1f(program.fogStart, fogStart);
                }
            }
            if (fog.fogEnd != this.fogEnd) {
                this.fogEnd = fog.fogEnd;
                if (program.fogEnd != -1) {
                    GL20.glUniform1f(program.fogEnd, fogEnd);
                }
            }
        } else {
            if (fog.fogDensity != this.fogDensity) {
                this.fogDensity = fog.fogDensity;
                if (program.fogDensity != -1) {
                    GL20.glUniform1f(program.fogDensity, fogDensity);
                }
            }
        }
    }

    private static boolean eq(float[] a, float[] b) {
        for (int i = 0; i < 4; i++)
            if (a[i] != b[i])
                return false;
        return true;
    }
}
