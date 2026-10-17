domain: example_flat
rules:
 ### Sliding Window Counter
- tryConsume : `synchronised` won't block another's only impact same user when hits multiple request at once
  - normal case not impact as compared with Lock Free CAS
  - High Concurrency (>100 req) slight up in latency if user hits in exact same milli-sec
  - `verdict` : `synchronised` to prevent notorious hammering system

# Rule 2: Limits for a specific message type
- key: message_type
  value: marketing
  policy:
      name: "marketing_limit"
      limit: 100
      unit: day
  replaces: ["specific_user_limit"] # Conflict resolution still works here