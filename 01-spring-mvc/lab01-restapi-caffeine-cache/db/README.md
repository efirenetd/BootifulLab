### Issue: Docker compose permission denied with volume

### Error:  
```/usr/local/bin/docker-entrypoint.sh: /docker-entrypoint-initdb.d/01-init.sh: /bin/bash: bad interpreter: Permission denied```

### Solution:
```chmod 777 /Users/jongtenerife/bootworkspace/BootifulLab/01-spring-mvc/lab01-restapi-redis-cache/db/01-init.sh```