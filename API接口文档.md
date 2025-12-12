# 静隅思录 - API 接口文档

## 基础信息
- 基础路径: `/api`
- 数据格式: JSON
- 字符编码: UTF-8

## 统一响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

| code | 说明 |
|------|------|
| 200 | 成功 |
| 400 | 参数错误 |
| 401 | 未登录 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器错误 |

---

## 一、用户模块 `/api/user`

### 1.1 用户登录
- **POST** `/api/user/login`

**请求参数:**
```json
{
  "username": "admin",
  "password": "123456"
}
```

**响应数据:**
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "id": 1,
    "username": "admin",
    "nickname": "林清远",
    "email": "admin@jingyusilu.com",
    "avatar": "/images/avatar.png",
    "bio": "一个热爱思考与写作的设计师",
    "token": "xxx.xxx.xxx"
  }
}
```

### 1.2 获取当前用户信息
- **GET** `/api/user/info`

**响应数据:**
```json
{
  "code": 200,
  "data": {
    "id": 1,
    "username": "admin",
    "nickname": "林清远",
    "email": "admin@jingyusilu.com",
    "avatar": "/images/avatar.png",
    "bio": "一个热爱思考与写作的设计师",
    "createdAt": "2024-01-01 00:00:00"
  }
}
```

### 1.3 更新用户信息
- **PUT** `/api/user/update`

**请求参数:**
```json
{
  "nickname": "林清远",
  "email": "new@email.com",
  "avatar": "/images/new-avatar.png",
  "bio": "新的个人简介"
}
```

---

## 二、分类模块 `/api/category`

### 2.1 获取所有分类
- **GET** `/api/category/list`

**响应数据:**
```json
{
  "code": 200,
  "data": [
    { "id": 1, "name": "设计沉思", "color": "#9B59B6", "count": 12 },
    { "id": 2, "name": "技术探索", "color": "#3498DB", "count": 8 },
    { "id": 3, "name": "生活随笔", "color": "#1ABC9C", "count": 15 },
    { "id": 4, "name": "读书笔记", "color": "#E67E22", "count": 6 },
    { "id": 5, "name": "哲学思辨", "color": "#E74C3C", "count": 4 }
  ]
}
```

---

## 三、文章模块 `/api/article`

### 3.1 获取文章列表
- **GET** `/api/article/list`

**请求参数 (Query):**
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | int | 否 | 页码，默认1 |
| size | int | 否 | 每页数量，默认10 |
| categoryId | long | 否 | 分类ID |
| keyword | string | 否 | 搜索关键词 |

**响应数据:**
```json
{
  "code": 200,
  "data": {
    "total": 45,
    "page": 1,
    "size": 10,
    "list": [
      {
        "id": 1,
        "title": "设计中的留白艺术：少即是多",
        "summary": "留白不是空白，而是一种设计语言...",
        "cover": "/images/cover1.png",
        "categoryId": 1,
        "categoryName": "设计沉思",
        "tags": "设计,留白,用户体验",
        "viewCount": 1256,
        "isPinned": true,
        "createdAt": "2024-07-15 10:30:00"
      }
    ]
  }
}
```

### 3.2 获取文章详情
- **GET** `/api/article/{id}`

**响应数据:**
```json
{
  "code": 200,
  "data": {
    "id": 1,
    "title": "设计中的留白艺术：少即是多",
    "summary": "留白不是空白，而是一种设计语言...",
    "content": "<p>文章HTML内容...</p>",
    "cover": "/images/cover1.png",
    "categoryId": 1,
    "categoryName": "设计沉思",
    "tags": "设计,留白,用户体验",
    "viewCount": 1256,
    "isPinned": true,
    "isPublished": true,
    "createdAt": "2024-07-15 10:30:00",
    "updatedAt": "2024-07-15 10:30:00",
    "author": {
      "id": 1,
      "nickname": "林清远",
      "avatar": "/images/avatar.png"
    }
  }
}
```

### 3.3 创建文章
- **POST** `/api/article/create`

**请求参数:**
```json
{
  "title": "文章标题",
  "summary": "文章摘要",
  "content": "<p>文章HTML内容</p>",
  "cover": "/images/cover.png",
  "categoryId": 1,
  "tags": "标签1,标签2",
  "isPinned": false,
  "isPublished": true
}
```

**响应数据:**
```json
{
  "code": 200,
  "message": "创建成功",
  "data": {
    "id": 10
  }
}
```

### 3.4 更新文章
- **PUT** `/api/article/update/{id}`

**请求参数:**
```json
{
  "title": "更新后的标题",
  "summary": "更新后的摘要",
  "content": "<p>更新后的内容</p>",
  "cover": "/images/new-cover.png",
  "categoryId": 2,
  "tags": "新标签1,新标签2",
  "isPinned": true,
  "isPublished": true
}
```

### 3.5 删除文章
- **DELETE** `/api/article/delete/{id}`

**响应数据:**
```json
{
  "code": 200,
  "message": "删除成功"
}
```

---

## 四、收藏模块 `/api/favorite`

### 4.1 获取收藏列表
- **GET** `/api/favorite/list`

**响应数据:**
```json
{
  "code": 200,
  "data": [
    {
      "id": 1,
      "articleId": 1,
      "title": "设计中的留白艺术：少即是多",
      "categoryName": "设计沉思",
      "createdAt": "2024-07-16 14:30:00"
    }
  ]
}
```

### 4.2 添加收藏
- **POST** `/api/favorite/add`

**请求参数:**
```json
{
  "articleId": 1
}
```

### 4.3 取消收藏
- **DELETE** `/api/favorite/remove/{articleId}`

---

## 五、浏览历史模块 `/api/history`

### 5.1 获取浏览历史
- **GET** `/api/history/list`

**请求参数 (Query):**
| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| page | int | 否 | 页码，默认1 |
| size | int | 否 | 每页数量，默认20 |

**响应数据:**
```json
{
  "code": 200,
  "data": [
    {
      "id": 1,
      "articleId": 1,
      "title": "设计中的留白艺术：少即是多",
      "categoryName": "设计沉思",
      "viewedAt": "2024-07-16 14:30:00"
    }
  ]
}
```

### 5.2 清空浏览历史
- **DELETE** `/api/history/clear`

---

## 六、统计模块 `/api/stats`

### 6.1 获取用户统计数据
- **GET** `/api/stats/user`

**响应数据:**
```json
{
  "code": 200,
  "data": {
    "articleCount": 45,
    "viewCount": 12580,
    "favoriteCount": 28
  }
}
```

---

## 接口汇总表

| 模块 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 用户 | POST | /api/user/login | 登录 |
| 用户 | GET | /api/user/info | 获取用户信息 |
| 用户 | PUT | /api/user/update | 更新用户信息 |
| 分类 | GET | /api/category/list | 获取分类列表 |
| 文章 | GET | /api/article/list | 获取文章列表 |
| 文章 | GET | /api/article/{id} | 获取文章详情 |
| 文章 | POST | /api/article/create | 创建文章 |
| 文章 | PUT | /api/article/update/{id} | 更新文章 |
| 文章 | DELETE | /api/article/delete/{id} | 删除文章 |
| 收藏 | GET | /api/favorite/list | 获取收藏列表 |
| 收藏 | POST | /api/favorite/add | 添加收藏 |
| 收藏 | DELETE | /api/favorite/remove/{id} | 取消收藏 |
| 历史 | GET | /api/history/list | 获取浏览历史 |
| 历史 | DELETE | /api/history/clear | 清空历史 |
| 统计 | GET | /api/stats/user | 用户统计数据 |
