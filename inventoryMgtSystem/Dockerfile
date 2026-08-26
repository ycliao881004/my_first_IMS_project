# ==========================================
# 階段 1：編譯打包 (Build Stage)
# ==========================================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# 先單獨複製 pom.xml 下載依賴（利用 Docker 快取層加速後續構建）
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 複製原始碼並打包（跳過測試以加速部署）
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# 階段 2：運行環境 (Runtime Stage)
# ==========================================
# 使用精簡的 JRE Alpine 映像檔，大幅縮減映像檔大小
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 從階段 1 複製打包好的 JAR 檔
COPY --from=build /app/target/*.jar app.jar

# 暴露 Spring Boot 預設的 8080 連接埠
EXPOSE 8080

# 啟動容器時執行的指令
ENTRYPOINT ["java", "-jar", "app.jar"]