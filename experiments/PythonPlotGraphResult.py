import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.dates as mdates

data = [
    {"timestamp": "2026-09-20 12:19:01", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "0.68%", "mem": "86.27%"},
    {"timestamp": "2026-09-20 12:19:01", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "0.09%", "mem": "98.93%"},
    {"timestamp": "2026-09-20 12:19:01", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.19%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:01", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "3.83%", "mem": "3.50%"},
    {"timestamp": "2026-09-20 12:19:06", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "1.08%", "mem": "87.14%"},
    {"timestamp": "2026-09-20 12:19:06", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "0.14%", "mem": "98.93%"},
    {"timestamp": "2026-09-20 12:19:06", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.18%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:06", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "6.82%", "mem": "3.51%"},
    {"timestamp": "2026-09-20 12:19:10", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "0.42%", "mem": "86.94%"},
    {"timestamp": "2026-09-20 12:19:10", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "0.08%", "mem": "98.93%"},
    {"timestamp": "2026-09-20 12:19:10", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.15%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:10", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "27.22%", "mem": "3.55%"},
    {"timestamp": "2026-09-20 12:19:14", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "2.57%", "mem": "86.40%"},
    {"timestamp": "2026-09-20 12:19:14", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "0.71%", "mem": "99.14%"},
    {"timestamp": "2026-09-20 12:19:14", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.20%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:14", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "4.07%", "mem": "3.50%"},
    {"timestamp": "2026-09-20 12:19:18", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "0.56%", "mem": "86.70%"},
    {"timestamp": "2026-09-20 12:19:18", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "108.52%", "mem": "99.99%"},
    {"timestamp": "2026-09-20 12:19:18", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "8.00%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:18", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "3.91%", "mem": "3.51%"},
    {"timestamp": "2026-09-20 12:19:18", "container": "01e41a1418c2c86fb403564e4f2e58909026f08340f794eccd23cf098c0c7816", "name": "vigilant_blackburn", "cpu": "5.16%", "mem": "0.31%"},
    {"timestamp": "2026-09-20 12:19:22", "container": "01e41a1418c2c86fb403564e4f2e58909026f08340f794eccd23cf098c0c7816", "name": "vigilant_blackburn", "cpu": "17.84%", "mem": "0.36%"},
    {"timestamp": "2026-09-20 12:19:22", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "2.04%", "mem": "93.23%"},
    {"timestamp": "2026-09-20 12:19:22", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "100.14%", "mem": "99.97%"},
    {"timestamp": "2026-09-20 12:19:22", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.18%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:22", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "23.53%", "mem": "3.64%"},
    {"timestamp": "2026-09-20 12:19:26", "container": "01e41a1418c2c86fb403564e4f2e58909026f08340f794eccd23cf098c0c7816", "name": "vigilant_blackburn", "cpu": "20.37%", "mem": "0.45%"},
    {"timestamp": "2026-09-20 12:19:26", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "0.29%", "mem": "92.97%"},
    {"timestamp": "2026-09-20 12:19:26", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "97.42%", "mem": "99.61%"},
    {"timestamp": "2026-09-20 12:19:26", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.17%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:26", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "32.93%", "mem": "3.58%"},
    {"timestamp": "2026-09-20 12:19:30", "container": "01e41a1418c2c86fb403564e4f2e58909026f08340f794eccd23cf098c0c7816", "name": "vigilant_blackburn", "cpu": "18.73%", "mem": "0.46%"},
    {"timestamp": "2026-09-20 12:19:30", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "0.42%", "mem": "92.97%"},
    {"timestamp": "2026-09-20 12:19:30", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "105.42%", "mem": "99.80%"},
    {"timestamp": "2026-09-20 12:19:30", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.18%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:30", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "8.51%", "mem": "3.52%"},
    {"timestamp": "2026-09-20 12:19:34", "container": "01e41a1418c2c86fb403564e4f2e58909026f08340f794eccd23cf098c0c7816", "name": "vigilant_blackburn", "cpu": "24.31%", "mem": "0.51%"},
    {"timestamp": "2026-09-20 12:19:34", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "0.31%", "mem": "92.98%"},
    {"timestamp": "2026-09-20 12:19:34", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "98.04%", "mem": "99.85%"},
    {"timestamp": "2026-09-20 12:19:34", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.17%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:34", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "11.13%", "mem": "3.53%"},
    {"timestamp": "2026-09-20 12:19:39", "container": "01e41a1418c2c86fb403564e4f2e58909026f08340f794eccd23cf098c0c7816", "name": "vigilant_blackburn", "cpu": "22.51%", "mem": "0.52%"},
    {"timestamp": "2026-09-20 12:19:39", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "0.45%", "mem": "93.30%"},
    {"timestamp": "2026-09-20 12:19:39", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "99.23%", "mem": "99.50%"},
    {"timestamp": "2026-09-20 12:19:39", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.20%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:39", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "8.68%", "mem": "3.52%"},
    {"timestamp": "2026-09-20 12:19:43", "container": "01e41a1418c2c86fb403564e4f2e58909026f08340f794eccd23cf098c0c7816", "name": "vigilant_blackburn", "cpu": "23.91%", "mem": "0.54%"},
    {"timestamp": "2026-09-20 12:19:43", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "0.28%", "mem": "93.30%"},
    {"timestamp": "2026-09-20 12:19:43", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "99.55%", "mem": "99.50%"},
    {"timestamp": "2026-09-20 12:19:43", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.17%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:43", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "20.85%", "mem": "3.56%"},
    {"timestamp": "2026-09-20 12:19:47", "container": "01e41a1418c2c86fb403564e4f2e58909026f08340f794eccd23cf098c0c7816", "name": "vigilant_blackburn", "cpu": "17.40%", "mem": "0.54%"},
    {"timestamp": "2026-09-20 12:19:47", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "0.37%", "mem": "93.03%"},
    {"timestamp": "2026-09-20 12:19:47", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "103.95%", "mem": "99.77%"},
    {"timestamp": "2026-09-20 12:19:47", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.16%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:47", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "37.02%", "mem": "3.66%"},
    {"timestamp": "2026-09-20 12:19:51", "container": "01e41a1418c2c86fb403564e4f2e58909026f08340f794eccd23cf098c0c7816", "name": "vigilant_blackburn", "cpu": "5.39%", "mem": "0.54%"},
    {"timestamp": "2026-09-20 12:19:51", "container": "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d", "name": "consumer-app", "cpu": "0.32%", "mem": "93.03%"},
    {"timestamp": "2026-09-20 12:19:51", "container": "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e", "name": "producer-app", "cpu": "74.50%", "mem": "99.85%"},
    {"timestamp": "2026-09-20 12:19:51", "container": "876054d60ad7ee8423f8442e12db89af1aec1c50b76f6be3e2c636356bfdb3fc", "name": "kafka-cluster-ui", "cpu": "0.14%", "mem": "2.38%"},
    {"timestamp": "2026-09-20 12:19:51", "container": "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5", "name": "broker", "cpu": "16.84%", "mem": "3.55%"}
]

df = pd.DataFrame(data)

container_map = {
    "26155891e3a92844b25f5b44514fd26978e3303c99e4cdef598643e88d39f91d": "consumer-app",
    "85daab3c7fcfc3b65f108a2b373dc2a329fd6be861e0364af688be4935d6c49e": "producer-app",
    "cb65b1e3f27b883f1a770d1f28b08e7fd70c4ccfb603a50c0987daa6100deeb5": "broker"
}

df['app_name'] = df['container'].map(container_map)
df = df.dropna(subset=['app_name']).copy()

df['cpu_num'] = pd.to_numeric(df['cpu'].str.replace('%', ''), errors='coerce')
df['mem_num'] = pd.to_numeric(df['mem'].str.replace('%', ''), errors='coerce')
df['timestamp'] = pd.to_datetime(df['timestamp'])

cutoff_time = pd.to_datetime("2026-09-20 12:19:52")
df = df[df['timestamp'] <= cutoff_time]

unique_timestamps = sorted(df['timestamp'].unique())

plt.style.use('seaborn-v0_8-whitegrid' if 'seaborn-v0_8-whitegrid' in plt.style.available else 'default')
fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(13, 9), sharex=True)

colors = {
    'consumer-app': '#1f77b4',
    'producer-app': '#d62728',
    'broker': '#2ca02c'
}

target_apps = ['consumer-app', 'producer-app', 'broker']

for app in target_apps:
    app_df = df[df['app_name'] == app].sort_values('timestamp').dropna(subset=['cpu_num', 'mem_num'])
    ax1.plot(app_df['timestamp'], app_df['cpu_num'], marker='o', linewidth=2, label=app, color=colors[app])
    ax2.plot(app_df['timestamp'], app_df['mem_num'], marker='s', linewidth=2, label=app, color=colors[app])

def add_peak_annotation(ax, timestamp, value, text, color, offset_y=25, arrow_down=True):
    ax.annotate(
        text,
        xy=(timestamp, value),
        xytext=(timestamp, value + offset_y if arrow_down else value - offset_y),
        ha='center',
        va='center',
        fontweight='bold',
        color=color,
        fontsize=10,
        arrowprops=dict(arrowstyle='->', color=color, lw=1.5)
    )

# 1. CPU Peak Annotations
prod_cpu_max = df[df['app_name'] == 'producer-app'].loc[df[df['app_name'] == 'producer-app']['cpu_num'].idxmax()]
add_peak_annotation(ax1, prod_cpu_max['timestamp'], prod_cpu_max['cpu_num'], f"Pico: {prod_cpu_max['cpu_num']:.2f}%", colors['producer-app'], offset_y=12)

broker_df = df[df['app_name'] == 'broker'].sort_values('timestamp')
p1 = broker_df[broker_df['cpu_num'] == 27.22].iloc[0]
add_peak_annotation(ax1, p1['timestamp'], p1['cpu_num'], f"Pico: {p1['cpu_num']:.2f}%", colors['broker'], offset_y=12)

p2 = broker_df[broker_df['cpu_num'] == 37.02].iloc[0]
add_peak_annotation(ax1, p2['timestamp'], p2['cpu_num'], f"Pico: {p2['cpu_num']:.2f}%", colors['broker'], offset_y=12)

cons_cpu_max = df[df['app_name'] == 'consumer-app'].loc[df[df['app_name'] == 'consumer-app']['cpu_num'].idxmax()]
add_peak_annotation(ax1, cons_cpu_max['timestamp'], cons_cpu_max['cpu_num'], f"Pico: {cons_cpu_max['cpu_num']:.2f}%", colors['consumer-app'], offset_y=15)

# 2. Memory Peak Annotations
prod_mem_max = df[df['app_name'] == 'producer-app'].loc[df[df['app_name'] == 'producer-app']['mem_num'].idxmax()]
add_peak_annotation(ax2, prod_mem_max['timestamp'], prod_mem_max['mem_num'], f"Pico: {prod_mem_max['mem_num']:.2f}%", colors['producer-app'], offset_y=-15, arrow_down=False)

cons_mem_max = df[df['app_name'] == 'consumer-app'].loc[df[df['app_name'] == 'consumer-app']['mem_num'].idxmax()]
add_peak_annotation(ax2, cons_mem_max['timestamp'], cons_mem_max['mem_num'], f"Pico: {cons_mem_max['mem_num']:.2f}%", colors['consumer-app'], offset_y=-15, arrow_down=False)

broker_mem_max = df[df['app_name'] == 'broker'].loc[df[df['app_name'] == 'broker']['mem_num'].idxmax()]
add_peak_annotation(ax2, broker_mem_max['timestamp'], broker_mem_max['mem_num'], f"Pico: {broker_mem_max['mem_num']:.2f}%", colors['broker'], offset_y=12)

# Formatting CPU plot
ax1.set_title('Consumo de CPU ao Longo do Tempo', fontsize=14, fontweight='bold', pad=12)
ax1.set_ylabel('Uso de CPU (%)', fontsize=12)
ax1.set_ylim(-5, 130)
ax1.legend(frameon=True, facecolor='white', loc='upper right')
ax1.grid(True, linestyle='--', alpha=0.7)

# Formatting Memory plot
ax2.set_title('Consumo de Memória RAM ao Longo do Tempo', fontsize=14, fontweight='bold', pad=12)
ax2.set_xlabel('Tempo (Segundos)', fontsize=12)
ax2.set_ylabel('Uso de Memória RAM (%)', fontsize=12)
ax2.set_ylim(-5, 120)
ax2.legend(frameon=True, facecolor='white', loc='center right')
ax2.grid(True, linestyle='--', alpha=0.7)

# Set exact X ticks and use mdates.DateFormatter('%S s')
ax2.set_xticks(unique_timestamps)
ax2.xaxis.set_major_formatter(mdates.DateFormatter('%S s'))

# Apply rotation=45 as requested
plt.xticks(rotation=45)

plt.tight_layout()
plt.savefig('container_metrics_rotation45.png', dpi=300)
plt.show()