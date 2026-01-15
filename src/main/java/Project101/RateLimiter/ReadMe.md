domain: example_flat
rules:
 # Rule 1: Limits for a specific user
- key: user 
  value: user02
  policy:
      name: "specific_user_limit"
      limit: 5
      unit: second

# Rule 2: Limits for a specific message type
- key: message_type
  value: marketing
  policy:
      name: "marketing_limit"
      limit: 100
      unit: day
  replaces: ["specific_user_limit"] # Conflict resolution still works here