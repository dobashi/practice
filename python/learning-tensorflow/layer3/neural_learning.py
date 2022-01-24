#!/usr/bin/env python
# -*- coding: utf-8 -*-

import tensorflow as tf

INPUT_SIZE = 15
W1_SIZE = 15
OUTPUT_SIZE = 10

## 入力と計算グラフを定義
x1 = tf.placeholder(dtype=tf.float32)
y = tf.placeholder(dtype=tf.float32)

# 第2層
tf.set_random_seed(1234)
W1 = tf.get_variable("W1",
                     shape=[INPUT_SIZE, W1_SIZE],
                     dtype=tf.float32,
                     initializer=tf.random_normal_initializer(stddev=0.05))
b1 = tf.get_variable("b1",
                     shape=[W1_SIZE],
                     dtype=tf.float32,
                     initializer=tf.random_normal_initializer(stddev=0.05))
x2 = tf.sigmoid(tf.matmul(x1, W1) + b1)

# 第3層
W2 = tf.get_variable("W2",
                     shape=[W1_SIZE, OUTPUT_SIZE],
                     dtype=tf.float32,
                     initializer=tf.random_normal_initializer(stddev=0.05))
b2 = tf.get_variable("b2",
                     shape=[OUTPUT_SIZE],
                     dtype=tf.float32,
                     initializer=tf.random_normal_initializer(stddev=0.05))
x3 = tf.nn.softmax(tf.matmul(x2, W2) + b2)

# コスト関数
cross_entropy = -tf.reduce_sum(y * tf.log(x3))

# 最適化アルゴリズムを定義
optimizer = tf.train.GradientDescentOptimizer(0.01)
minimize = optimizer.minimize(cross_entropy)

## データセットを読み込むためのパイプラインを作成する
# リーダーオブジェクトを作成する
reader = tf.TextLineReader()

# 読み込む対象のファイルを格納したキューを作成する
file_queue = tf.train.string_input_producer(["data.csv", "test.csv"])

# キューからデータを読み込む
key, value = reader.read(file_queue)

# 読み込んだCSV型式データをデコードする
# [[] for i in range(16)] は
# [[], [], [], [], [], [], [], [],
#  [], [], [], [], [], [], [], []]に相当
data = tf.decode_csv(value, record_defaults=[[] for i in range(16)])

# 10件のデータを読み出す
# 10件ずつデータを読み出す
# 第1カラム（data[0]）はその文字が示す数だが、
# ニューラルネットワークの出力は10要素の1次元テンソルとなる。
# そのため、10×10の対角行列を作成し、そのdata[0]行目を取り出す操作を行うことで
# 1次元テンソルに変換する。dataは浮動小数点小数型なので、このとき
# int32型にキャストして使用する
data_x, data_y, y_value = tf.train.batch([
    tf.stack(data[1:]),
    tf.reshape(tf.slice(tf.eye(10), [tf.cast(data[0], tf.int32), 0], [1, 10]), [10]),
    tf.cast(data[0], tf.int64),
], 10)

# セッションの作成
sess = tf.Session()

# 変数の初期化を実行する
sess.run(tf.global_variables_initializer())

# コーディネータの作成
coord = tf.train.Coordinator()

# キューの開始
threads = tf.train.start_queue_runners(sess=sess, coord=coord)

# ファイルからのデータの読み出し
# 1回目のデータ読み込み。1つ目のファイルから10件のデータが読み込まれる
# 1つ目のファイルには10件のデータがあるので、これで全データが読み込まれる
dataset_x, dataset_y, values_y = sess.run([data_x, data_y, y_value])

# 2回目のデータ読み込み。1つ目のファイルのデータはすべて読み出したので、
# 続けて2つ目のファイルから読み込みが行われる。
testdata_x, testdata_y, testvalues_y = sess.run([data_x, data_y, y_value])

# 学習を開始
for i in range(100):
    for j in range(100):
        sess.run(minimize, {x1: dataset_x, y: dataset_y})
    print("CROSS ENTROPY:", sess.run(cross_entropy, {x1: dataset_x, y: dataset_y}))

## 結果の出力
# 出力テンソルの中でもっとも値が大きいもののインデックスが
# 正答と等しいかどうかを計算する
y_value = tf.placeholder(dtype=tf.int64)
correct = tf.equal(tf.argmax(x3,1), y_value)
accuracy = tf.reduce_mean(tf.cast(correct, "float"))

# 学習に使用したデータを入力した場合の
# ニューラルネットワークの出力を表示
print("----result----")
print("raw output:")
print(sess.run(x3,feed_dict={x1: dataset_x}))
print("answers:", sess.run(tf.argmax(x3, 1), feed_dict={x1: dataset_x}))

# このときの正答率を出力
print("accuracy:", sess.run(accuracy, feed_dict={x1: dataset_x, y_value: values_y}))
        

# テスト用データを入力した場合の
# ニューラルネットワークの出力を表示
print("----test----")
print("raw output:")
print(sess.run(x3,feed_dict={x1: testdata_x}))
print("answers:", sess.run(tf.argmax(x3, 1), feed_dict={x1: testdata_x}))

# このときの正答率を出力
print("accuracy:", sess.run(accuracy, feed_dict={x1: testdata_x, y_value: testvalues_y}))


# キューの終了
coord.request_stop()
coord.join(threads)
