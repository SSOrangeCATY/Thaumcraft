package net.ssorangecaty.elementreborn.core.node;

public enum NodeModifier {
    COMMON(0.75f),
    BRIGHT(0.95f),
    PALE(0.55f),
    FADING(0.25f);

    final public float alpha;
    NodeModifier(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }
}