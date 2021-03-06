/*
    Copyright (C) 2014 Infinite Automation Systems Inc. All rights reserved.
    @author Matthew Lohbihler
 */
package com.serotonin.m2m2.gviews.component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.serotonin.json.spi.JsonProperty;
import com.serotonin.m2m2.i18n.Translations;
import com.serotonin.m2m2.view.ImplDefinition;
import com.serotonin.util.SerializationHelper;

/**
 * @author Matthew Lohbihler
 */
public class SimpleCompoundComponent extends CompoundComponent {
    public static ImplDefinition DEFINITION = new ImplDefinition("simpleCompound", "SIMPLE_COMPOUND",
            "graphic.simpleCompound", null);

    public static final String LEAD_POINT = "leadPoint";
    public static final String SUB_POINT_1 = "subPoint1";
    public static final String SUB_POINT_2 = "subPoint2";
    public static final String SUB_POINT_3 = "subPoint3";
    public static final String SUB_POINT_4 = "subPoint4";
    public static final String SUB_POINT_5 = "subPoint5";
    public static final String SUB_POINT_6 = "subPoint6";
    public static final String SUB_POINT_7 = "subPoint7";
    public static final String SUB_POINT_8 = "subPoint8";
    public static final String SUB_POINT_9 = "subPoint9";
    public static final String SUB_POINT_10 = "subPoint10";

    @JsonProperty
    private String backgroundColour;

    public SimpleCompoundComponent() {
        initialize();
    }

    @Override
    protected void initialize() {
        SimplePointComponent lead = new SimplePointComponent();
        lead.setLocation(0, 0);
        lead.setStyle("position:relative;");
        lead.setDisplayControls(false);
        lead.setSettableOverride(false);
        addChild(LEAD_POINT, "graphic.simpleCompound." + LEAD_POINT, lead, null);

        addChild(SUB_POINT_1, "graphic.simpleCompound." + SUB_POINT_1, createComponent(), null);
        addChild(SUB_POINT_2, "graphic.simpleCompound." + SUB_POINT_2, createComponent(), null);
        addChild(SUB_POINT_3, "graphic.simpleCompound." + SUB_POINT_3, createComponent(), null);
        addChild(SUB_POINT_4, "graphic.simpleCompound." + SUB_POINT_4, createComponent(), null);
        addChild(SUB_POINT_5, "graphic.simpleCompound." + SUB_POINT_5, createComponent(), null);
        addChild(SUB_POINT_6, "graphic.simpleCompound." + SUB_POINT_6, createComponent(), null);
        addChild(SUB_POINT_7, "graphic.simpleCompound." + SUB_POINT_7, createComponent(), null);
        addChild(SUB_POINT_8, "graphic.simpleCompound." + SUB_POINT_8, createComponent(), null);
        addChild(SUB_POINT_9, "graphic.simpleCompound." + SUB_POINT_9, createComponent(), null);
        addChild(SUB_POINT_10, "graphic.simpleCompound." + SUB_POINT_10, createComponent(), null);
    }

    @Override
    public boolean hasInfo() {
        return false;
    }

    private SimplePointComponent createComponent() {
        SimplePointComponent c = new SimplePointComponent();
        c.setLocation(0, 0);
        c.setStyle("position:relative;");
        c.setDisplayControls(true);
        c.setSettableOverride(true);
        c.setDisplayPointName(true);
        c.setBkgdColorOverride("transparent");
        return c;
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    @Override
    public ImplDefinition definition() {
        return DEFINITION;
    }

    public ViewComponent getLeadComponent() {
        return getChildComponent(LEAD_POINT);
    }

    @Override
    public String getStaticContent() {
        return null;
    }

    @Override
    public boolean isDisplayImageChart() {
        return false;
    }

    @Override
    public String getImageChartData(Translations translations) {
        return null;
    }

    //
    // /
    // / Serialization
    // /
    //
    private static final long serialVersionUID = -1;
    private static final int version = 1;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(version);
        SerializationHelper.writeSafeUTF(out, backgroundColour);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        int ver = in.readInt();

        // Switch on the version of the class so that version changes can be elegantly handled.
        if (ver == 1)
            backgroundColour = SerializationHelper.readSafeUTF(in);
    }
}
