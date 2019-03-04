
package com.example.tvlaunchertoukir.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MediaModel implements Parcelable {

    private int id;
    private String title;
    private String content;
    private String imageUrl;
    private String videoUrl;

    private MediaModel(
            final int id,
            final String title,
            final String content,
            final String imageUrl,
            final String videoUrl) {

        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }

    protected MediaModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        imageUrl = in.readString();
        videoUrl = in.readString();
    }

    public static final Creator<MediaModel> CREATOR = new Creator<MediaModel>() {
        @Override
        public MediaModel createFromParcel(Parcel in) {
            return new MediaModel(in);
        }

        @Override
        public MediaModel[] newArray(int size) {
            return new MediaModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public static List<MediaModel> getPhotoModels() {
        List<MediaModel> mediaModels = new ArrayList<>();

        String titles[] = {
                "Beijing",
                "Shanghai",
                "Tibet",
                "Fan Bingbing",
                "High round",
                "Michael Jackson",
        };

        String contents[] = {
                "Quid de Platone aut de Democrito loquar? Quae contraria sunt his, malane? Sed haec omittamus; Laelius clamores sofòw ille so lebat Edere compellans gumias ex ordine nostros. Quando enim Socrates, qui parens philosophiae iure dici potest, quicquam tale fecit? Si verbum sequimur, primum longius verbum praepositum quam bonum.",
                "Respondeat totidem verbis. Aliter homines, aliter philosophos loqui putas oportere? Quae quidem vel cum periculo est quaerenda vobis; Equidem soleo etiam quod uno Graeci, si aliter non possum, idem pluribus verbis exponere. Quamquam haec quidem praeposita recte et reiecta dicere licebit. Deinde disputat, quod cuiusque generis animantium statui deceat extremum. Nam quid possumus facere melius? Quo igitur, inquit, modo?",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quorum sine causa fieri nihil putandum est. Themistocles quidem, cum ei Simonides an quis alius artem memoriae polliceretur, Oblivionis, inquit, mallem. Duo Reges: constructio interrete. Nunc ita separantur, ut disiuncta sint, quo nihil potest esse perversius. Rationis enim perfectio est virtus; Illa argumenta propria videamus, cur omnia sint paria peccata. Quae similitudo in genere etiam humano apparet.",
                "Respondeat totidem verbis. Aliter homines, aliter philosophos loqui putas oportere? Quae quidem vel cum periculo est quaerenda vobis; Equidem soleo etiam quod uno Graeci, si aliter non possum, idem pluribus verbis exponere. Quamquam haec quidem praeposita recte et reiecta dicere licebit. ",
                "Respondeat totidem verbis. Aliter homines, aliter philosophos loqui putas oportere? Quae quidem vel cum periculo est quaerenda vobis; Equidem soleo etiam quod uno Graeci, si aliter non possum, idem pluribus verbis exponere. Quamquam haec quidem praeposita recte et reiecta dicere licebit. ",
                "Respondeat totidem verbis. Aliter homines, aliter philosophos loqui putas oportere? Quae quidem vel cum periculo est quaerenda vobis; Equidem soleo etiam quod uno Graeci, si aliter non possum, idem pluribus verbis exponere. Quamquam haec quidem praeposita recte et reiecta dicere licebit. ",
        };

        String urls[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
                "http://e.hiphotos.baidu.com/zhidao/pic/item/5ab5c9ea15ce36d3418e754838f33a87e850b1c4.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose/card.jpg",
        };

        for (int i = 0; i < titles.length; i++) {
            MediaModel mediaModel = new MediaModel(0, titles[i], contents[i], urls[i], "");
            mediaModels.add(mediaModel);
        }

        return mediaModels;
    }

    public static List<MediaModel> getVideoModels() {
        List<MediaModel> mediaModels = new ArrayList<>();

        String titles[] = {
                "CCTV1",
                "湖南卫视",
        };

        String contents[] = {
                "\n" +
                        "CCTV's comprehensive channel (channel call sign: CCTV-1 integrated) is a comprehensive TV channel based on news. It is the first program of CCTV. It was broadcast on September 2, 1958. \\n\" +\n" +
                        "                        \"On May 1, 1958, CCTV-1 began trial broadcasting, and called \"Beijing TV Station.\" On May 1, 1978, \"Beijing TV Station\" was officially renamed CCTV. On April 3, 1995, CCTV-1 officially Changed its name to \"News·Comprehensive Channel\". \" +\n" +
                        "                        \"On May 1, 2003, CCTV-1 was changed from news and comprehensive channel to integrated channel. On September 27, 2009, CCTV-1 broadcasted high-definition signals to achieve high-definition and standard-definition broadcast. In March 2011, CCTV-1 The version is the domestic version and the Hong Kong version (the Hong Kong version was originally broadcast by Asian TV and later changed to NOW TV broadcast).）",

                "Hunan Satellite TV, the full name of Hunan Radio and Television Satellite Channel, nicknamed Mango Taiwan, is a satellite TV channel owned by Hunan Radio and Television Station and Mango Media Co., Ltd. \\n\" +\n" +
                        "                        \"In January 1997, Hunan TV's first program was renamed Hunan Satellite TV Satellite Channel. \\n\" +\n" +
                        "                        \"In January 2010, after the integration of Hunan Radio and Television, the call sign was renamed \"Hunan Radio and Television Satellite Channel\".\\n\" +\n" +
                        "                        \"In April 2016, Hunan Satellite TV launched the channel slogan \"The more fresh and younger\".",
        };

        String urls[] = {
                "http://hiphotos.baidu.com/%B1%A1%B1%CC%C0%B6%C9%C0/pic/item/e0d2dc358b514685d1a2d3fa.jpg",
                "http://imgsrc.baidu.com/forum/pic/item/410e5aafa40f4bfbd8a9a68a034f78f0f63618fa.jpg",
        };

        String videoUrls[] = {
                "http://eshare.live.otvcloud.com/otv/nyz/live/channel01/index.m3u8",
                "http://111.39.226.103:8112/120000001001/wlds:8080/ysten-business/live/hdhunanstv/.m3u8",
        };

        for (int i = 0; i < titles.length; i++) {
            MediaModel mediaModel = new MediaModel(
                    0, titles[i], contents[i], urls[i], videoUrls[i]);
            mediaModels.add(mediaModel);
        }

        return mediaModels;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(imageUrl);
        dest.writeString(videoUrl);
    }
}
