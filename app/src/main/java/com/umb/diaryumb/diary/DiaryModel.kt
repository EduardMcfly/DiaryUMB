package com.umb.diaryumb.diary

import com.umb.diaryumb.Database
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class DiaryModel : Database() {
    fun getAll(): ArrayList<Diary> {
        val data: ArrayList<Diary> = ArrayList<Diary>()
        val c = connection
        if (c != null) {
            var s = c.createStatement()
            var result: ResultSet = s.executeQuery("SELECT * FROM agendas")


            while (result.next()) {
                val diary = Diary(
                    id = result.getLong("id"),
                    date = result.getString("date"),
                    affair = result.getString("affair"),
                    activity = result.getString("activity"),
                )
                data.add(diary)
            }
        }
        c.close()
        return data
    }

    fun add(
        date: String,
        affair: String,
        activity: String
    ): Diary {
        val c = connection
        val returnId = arrayOf("id")

        val query =
            "INSERT INTO \"public\".\"agendas\"(\"date\", \"affair\", \"activity\") VALUES ('%s', '%s', '%s')"
        val s = c.prepareStatement(
            query.format(date, affair, activity),
            returnId
        )
        val affectedRows: Int = s.executeUpdate()
        if (affectedRows == 0) {
            throw SQLException("Creating user failed, no rows affected.");
        }
        val res = s.generatedKeys;
        val diary = Diary(
            null,
            date,
            affair,
            activity
        )
        if (res.next())
            diary.id = res.getLong(1)
        c.close()
        return diary
    }

    fun delete(id: Long) {

        val c = connection

        val query =
            "DELETE FROM \"public\".\"agendas\" WHERE \"id\" = %s"
        val s = c.prepareStatement(
            query.format(id),
        )
        val affectedRows: Int = s.executeUpdate()
        if (affectedRows == 0) {
            throw SQLException("Creating user failed, no rows affected.");
        }

        c.close()
    }


}