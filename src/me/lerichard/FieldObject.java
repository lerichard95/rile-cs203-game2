package me.lerichard;

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

}
