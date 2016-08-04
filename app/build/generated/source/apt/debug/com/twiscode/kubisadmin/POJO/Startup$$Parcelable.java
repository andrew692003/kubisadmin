
package com.twiscode.kubisadmin.POJO;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2016-08-04T09:57+0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Startup$$Parcelable
    implements Parcelable, ParcelWrapper<com.twiscode.kubisadmin.POJO.Startup>
{

    private com.twiscode.kubisadmin.POJO.Startup startup$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Startup$$Parcelable.Creator$$0 CREATOR = new Startup$$Parcelable.Creator$$0();

    public Startup$$Parcelable(com.twiscode.kubisadmin.POJO.Startup startup$$2) {
        startup$$0 = startup$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(startup$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twiscode.kubisadmin.POJO.Startup startup$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(startup$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(startup$$1));
            parcel$$1 .writeString(startup$$1 .thumbnail);
            if (startup$$1 .founder == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(startup$$1 .founder.size());
                for (java.lang.String string$$0 : ((java.util.ArrayList<java.lang.String> ) startup$$1 .founder)) {
                    parcel$$1 .writeString(string$$0);
                }
            }
            parcel$$1 .writeString(startup$$1 .creatorId);
            if (startup$$1 .isDisplayed == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeInt((startup$$1 .isDisplayed? 1 : 0));
            }
            parcel$$1 .writeString(startup$$1 .description);
            parcel$$1 .writeString(startup$$1 .type);
            if (startup$$1 .imageUrl == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(startup$$1 .imageUrl.size());
                for (java.lang.String string$$1 : ((java.util.ArrayList<java.lang.String> ) startup$$1 .imageUrl)) {
                    parcel$$1 .writeString(string$$1);
                }
            }
            if (startup$$1 .upvoters == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(startup$$1 .upvoters.size());
                for (java.lang.String string$$2 : ((java.util.ArrayList<java.lang.String> ) startup$$1 .upvoters)) {
                    parcel$$1 .writeString(string$$2);
                }
            }
            parcel$$1 .writeString(startup$$1 .linkUrl);
            parcel$$1 .writeString(startup$$1 .name);
            if (startup$$1 .status == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeInt((startup$$1 .status? 1 : 0));
            }
            if (startup$$1 .hashtag == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(startup$$1 .hashtag.size());
                for (java.lang.String string$$3 : ((java.util.ArrayList<java.lang.String> ) startup$$1 .hashtag)) {
                    parcel$$1 .writeString(string$$3);
                }
            }
            if (startup$$1 .timestamp == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(1);
                parcel$$1 .writeLong(startup$$1 .timestamp);
            }
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twiscode.kubisadmin.POJO.Startup getParcel() {
        return startup$$0;
    }

    public static com.twiscode.kubisadmin.POJO.Startup read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twiscode.kubisadmin.POJO.Startup startup$$3;
            int reservation$$0 = identityMap$$1 .reserve();
            startup$$3 = new com.twiscode.kubisadmin.POJO.Startup();
            identityMap$$1 .put(reservation$$0, startup$$3);
            startup$$3 .thumbnail = parcel$$3 .readString();
            int int$$0 = parcel$$3 .readInt();
            java.util.ArrayList<java.lang.String> list$$0;
            if (int$$0 < 0) {
                list$$0 = null;
            } else {
                list$$0 = new java.util.ArrayList<java.lang.String>(int$$0);
                for (int int$$1 = 0; (int$$1 <int$$0); int$$1 ++) {
                    list$$0 .add(parcel$$3 .readString());
                }
            }
            startup$$3 .founder = list$$0;
            startup$$3 .creatorId = parcel$$3 .readString();
            int int$$2 = parcel$$3 .readInt();
            java.lang.Boolean boolean$$0;
            if (int$$2 < 0) {
                boolean$$0 = null;
            } else {
                boolean$$0 = (parcel$$3 .readInt() == 1);
            }
            startup$$3 .isDisplayed = boolean$$0;
            startup$$3 .description = parcel$$3 .readString();
            startup$$3 .type = parcel$$3 .readString();
            int int$$3 = parcel$$3 .readInt();
            java.util.ArrayList<java.lang.String> list$$1;
            if (int$$3 < 0) {
                list$$1 = null;
            } else {
                list$$1 = new java.util.ArrayList<java.lang.String>(int$$3);
                for (int int$$4 = 0; (int$$4 <int$$3); int$$4 ++) {
                    list$$1 .add(parcel$$3 .readString());
                }
            }
            startup$$3 .imageUrl = list$$1;
            int int$$5 = parcel$$3 .readInt();
            java.util.ArrayList<java.lang.String> list$$2;
            if (int$$5 < 0) {
                list$$2 = null;
            } else {
                list$$2 = new java.util.ArrayList<java.lang.String>(int$$5);
                for (int int$$6 = 0; (int$$6 <int$$5); int$$6 ++) {
                    list$$2 .add(parcel$$3 .readString());
                }
            }
            startup$$3 .upvoters = list$$2;
            startup$$3 .linkUrl = parcel$$3 .readString();
            startup$$3 .name = parcel$$3 .readString();
            int int$$7 = parcel$$3 .readInt();
            java.lang.Boolean boolean$$1;
            if (int$$7 < 0) {
                boolean$$1 = null;
            } else {
                boolean$$1 = (parcel$$3 .readInt() == 1);
            }
            startup$$3 .status = boolean$$1;
            int int$$8 = parcel$$3 .readInt();
            java.util.ArrayList<java.lang.String> list$$3;
            if (int$$8 < 0) {
                list$$3 = null;
            } else {
                list$$3 = new java.util.ArrayList<java.lang.String>(int$$8);
                for (int int$$9 = 0; (int$$9 <int$$8); int$$9 ++) {
                    list$$3 .add(parcel$$3 .readString());
                }
            }
            startup$$3 .hashtag = list$$3;
            int int$$10 = parcel$$3 .readInt();
            Long long$$0;
            if (int$$10 < 0) {
                long$$0 = null;
            } else {
                long$$0 = parcel$$3 .readLong();
            }
            startup$$3 .timestamp = long$$0;
            return startup$$3;
        }
    }

    public final static class Creator$$0
        implements Creator<Startup$$Parcelable>
    {


        @Override
        public Startup$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Startup$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Startup$$Parcelable[] newArray(int size) {
            return new Startup$$Parcelable[size] ;
        }

    }

}
