package me.lerichard;

import javalib.worldimages.DiskImage;
import javalib.worldimages.FrameImage;
import javalib.worldimages.WorldImage;

import java.awt.*;

/**
 * Created by richard on 4/17/15.
 */
public class FieldObject {
    Coord myCoords;
    FieldObjectType type;

    public FieldObject(Coord myCoords, FieldObjectType type) {
        this.myCoords = myCoords;
        this.type = type;
    }

    /**
     * Returns an empty field object at the location
     *
     * @return
     */
    public FieldObject empty() {
        return new FieldObject(this.myCoords, FieldObjectType.MT);
    }

    /**
     * Changes coordinate of the FieldObject
     *
     * @param cc The new coord to change to
     * @return Returns a new FieldObject with modified coordinates but same type
     */
    public FieldObject newCoords(Coord cc) {
        return new FieldObject(cc, this.type);

    }


    /**
     * String rep of FieldObject
     *
     * @return String
     */
    @Override
    public String toString() {
        return "FieldObject{" +
                "myCoords=" + myCoords +
                ", type=" + type +
                '}';
    }

    /**
     * Returns a WorldImage of the FieldObject
     *
     * @return
     */
    public WorldImage makeImage() {
        WorldImage output = new FrameImage(this.myCoords.CoordToPinhole(),
                FieldWorld.FIELD_OBJECT_RADIUS, FieldWorld.FIELD_OBJECT_RADIUS, Color.BLACK);

        if (this.type.equals(FieldObjectType.TREASURE)) {
            output = new DiskImage(this.myCoords.CoordToPinhole(), FieldWorld.FIELD_OBJECT_RADIUS,
                    Color.yellow);
        }

        if (this.type.equals(FieldObjectType.PLAYER)) {
            output = new DiskImage(this.myCoords.CoordToPinhole(), FieldWorld.FIELD_OBJECT_RADIUS,
                    Color.blue);
        }
        return output;
    }

}
