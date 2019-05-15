package com.example.viewmodel.model

import android.os.Parcel
import android.os.Parcelable

data class Repository(
    var id: Int
    , var name: String?
    , var description: String?
    , var forks: Int
    , var watchers: Int
    , var stargazers_count: Int
    , var language: String?
    , var homepage: String?
    , var owner: User?
    , var fork: Boolean
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readParcelable<User>(User::class.java.classLoader),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeString(description)
        writeInt(forks)
        writeInt(watchers)
        writeInt(stargazers_count)
        writeString(language)
        writeString(homepage)
        writeParcelable(owner, 0)
        writeInt((if (fork) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Repository> = object : Parcelable.Creator<Repository> {
            override fun createFromParcel(source: Parcel): Repository = Repository(source)
            override fun newArray(size: Int): Array<Repository?> = arrayOfNulls(size)
        }
    }
}

data class User(
    var id: Int
    , var name: String?
    , var url: String?
    , var email: String?
    , var login: String?
    , var location: String?
    , var avatar_url: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeString(url)
        writeString(email)
        writeString(login)
        writeString(location)
        writeString(avatar_url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}