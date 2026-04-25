package com.mycompany.uniproject;
public class RoomPreferences {
    private RoomType preferredRoomType;
    private int preferredFloor;
    private boolean smokingAllowed;
    private boolean seaView;

    public RoomPreferences(RoomType preferredRoomType, int preferredFloor, boolean smokingAllowed, boolean seaView) {
        this.preferredRoomType = preferredRoomType;
        this.preferredFloor = preferredFloor;
        this.smokingAllowed = smokingAllowed;
        this.seaView = seaView;
    }

    public RoomType getPreferredRoomType() { return preferredRoomType; }
    public void setPreferredRoomType(RoomType preferredRoomType) { this.preferredRoomType = preferredRoomType; }
    public int getPreferredFloor() { return preferredFloor; }
    public void setPreferredFloor(int preferredFloor) { this.preferredFloor = preferredFloor; }
    public boolean isSmokingAllowed() { return smokingAllowed; }
    public void setSmokingAllowed(boolean smokingAllowed) { this.smokingAllowed = smokingAllowed; }
    public boolean isSeaView() { return seaView; }
    public void setSeaView(boolean seaView) { this.seaView = seaView; }

    @Override
    public String toString() { return "RoomPreferences{type=" + preferredRoomType + ", floor=" + preferredFloor + ", smoking=" + smokingAllowed + ", seaView=" + seaView + "}"; }
}
