package com.test.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //将maptask传给我们的一行的文本内容先转化成String
        String line = value.toString();
        //根据空格将这一行切分成单词
        String[] words = line.split(" ");

        //将单词输出为<单词,1>
        for (String word: words
             ) {
            //将单词作为key，将次数1作为value，以便于后续的数据分发。
            context.write(new Text(word),new IntWritable(1));
        }
    }
}
