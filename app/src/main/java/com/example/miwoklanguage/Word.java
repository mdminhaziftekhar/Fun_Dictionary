package com.example.miwoklanguage;

public class Word {
    private final String mDefaultTranslation;
    private final String mMiwokTranslation;
    private final int mImageResourceId;
    private final int mAudioResourceId;
    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(final String defaultTranslation, final String miwokTranslation,
                final int imageResourceId, final int audioResourceId) {
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
        this.mImageResourceId = imageResourceId;
        this.mAudioResourceId = audioResourceId;
    }

    public Word(final String defaultTranslation, final String miwokTranslation,
                final int audioResourceId) {
        this(defaultTranslation, miwokTranslation, NO_IMAGE_PROVIDED, audioResourceId);
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getAudioResourceId() {return mAudioResourceId; }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }
}
