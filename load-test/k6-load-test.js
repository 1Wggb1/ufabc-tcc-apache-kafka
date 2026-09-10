import http from 'k6/http';
import { sleep, check } from 'k6';

const requests = [
    ['POST', 'http://producer-app:8090/produces', null],
];

export const options = {
  stages: [
    { duration: '1s', target: 10 },
  ],
  thresholds: Object.fromEntries(
    ['http_req_duration', 'http_reqs', 'http_req_failed']
      .flatMap(metric => requests.map(request => [ `${metric}{url:${request[1]}}`, []]))),
};

export default function() {
  const responses = http.batch(requests);
  check(responses[0], { "status is200": (res) => res.status === 200 });
  sleep(1);
}