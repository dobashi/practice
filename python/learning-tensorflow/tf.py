#!/usr/bin/env python

import tensorflow as tf

a = tf.get_variable("a", shape=[1], dtype=tf.float32)
b = tf.get_variable("b", shape=[1], dtype=tf.float32)

x = tf.placeholder(name="x", dtype=tf.float32)
y = tf.placeholder(name="y", dtype=tf.float32)

square_error = tf.square(y - a*x - b)

rss = tf.reduce_sum(square_error)

optimizer = tf.train.GradientDescentOptimizer(1.0e-5)
minimize = optimizer.minimize(rss)

# data reader
reader = tf.TextLineReader()

file_queue = tf.train.string_input_producer(["data.csv"])
key, value = reader.read(file_queue)
col1, col2 = tf.decode_csv(value, [[], []])
data_x, data_y = tf.train.batch([col1, col2], 100)

sess = tf.Session()
sess.run(tf.global_variables_initializer())
coord = tf.train.Coordinator()
threads = tf.train.start_queue_runners(sess=sess, coord=coord)
dataset_x, dataset_y = sess.run([data_x, data_y])

a.load([0.0], sess)
b.load([0.0], sess)

# 初期状態のRSSを取得
result = sess.run(rss, {x: dataset_x, y: dataset_y})
print("RSS:", result)

# ループ開始
for i in range(10000):
    for j in range(1000):
        sess.run(minimize, {x: dataset_x, y: dataset_y})
    # RSSを取得し、以前のものと比べて小さくなっていなかったら終了
    result_after = sess.run(rss, {x: dataset_x, y: dataset_y})
    print("RSS:", result_after)
    if (result_after >= result):
        break
    result = result_after
print("done.")

# スレッドの終了待機
coord.request_stop()
coord.join(threads)

# 結果の出力
print("results: [a, b] =", sess.run([a, b]))
print("RSS is", sess.run(rss, {x: dataset_x, y: dataset_y}))

# データセット生成に使用したa、bの値を使用した場合のRSSを計算する
a.load([10.0], sess)
b.load([-4.0], sess)
print("true [a, b] is", sess.run([a, b]))








