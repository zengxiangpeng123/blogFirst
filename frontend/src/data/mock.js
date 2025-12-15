// 假数据
export const author = {
  name: '林清远',
  avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=Felix',
  bio: '一个热爱思考与写作的设计师，专注于用户体验与产品设计。',
  email: 'contact@jingyusilu.com',
  github: 'https://github.com',
  twitter: 'https://twitter.com'
}

export const categories = [
  { 
    name: '设计沉思', 
    count: 12,
    children: [
      { name: 'UI设计', count: 5 },
      { name: 'UX体验', count: 4 },
      { name: '设计理论', count: 3 }
    ]
  },
  { 
    name: '技术探索', 
    count: 8,
    children: [
      { name: '前端开发', count: 4 },
      { name: '后端开发', count: 2 },
      { name: '工具分享', count: 2 }
    ]
  },
  { 
    name: '生活随笔', 
    count: 15,
    children: [
      { name: '日常记录', count: 8 },
      { name: '旅行见闻', count: 4 },
      { name: '美食探店', count: 3 }
    ]
  },
  { 
    name: '读书笔记', 
    count: 6,
    children: [
      { name: '技术书籍', count: 3 },
      { name: '人文社科', count: 3 }
    ]
  },
  { 
    name: '哲学思辨', 
    count: 4,
    children: [
      { name: '人生感悟', count: 2 },
      { name: '思维方式', count: 2 }
    ]
  }
]

export const articles = [
  {
    id: 1,
    title: '设计中的留白艺术：少即是多',
    category: '设计沉思',
    date: '2024-07-15',
    readTime: 8,
    views: 1256,
    summary: '留白不是空白，而是一种设计语言。它给予内容呼吸的空间，让用户的视线有处安放。本文探讨如何在界面设计中恰当运用留白，创造优雅而高效的用户体验。',
    tags: ['设计', '留白', '用户体验'],
    pinned: true
  },
  {
    id: 2,
    title: '从Vue2到Vue3：一次渐进式的迁移之旅',
    category: '技术探索',
    date: '2024-07-10',
    readTime: 12,
    views: 2341,
    summary: '记录将一个中型项目从Vue2迁移到Vue3的完整过程，包括Composition API的应用、响应式系统的变化，以及遇到的坑与解决方案。',
    tags: ['Vue', '前端', '技术'],
    pinned: true
  },
  {
    id: 3,
    title: '夏日午后的咖啡馆',
    category: '生活随笔',
    date: '2024-07-05',
    readTime: 5,
    views: 892,
    summary: '在城市的某个角落，有一家不起眼的咖啡馆。阳光透过玻璃窗洒在木质桌面上，空气中弥漫着咖啡的香气。这里是我思考与写作的秘密基地。',
    tags: ['生活', '咖啡', '随笔']
  },
  {
    id: 4,
    title: '《设计心理学》读后感',
    category: '读书笔记',
    date: '2024-06-28',
    readTime: 10,
    views: 756,
    summary: '唐纳德·诺曼的经典之作，让我重新审视日常生活中的设计。好的设计应该是不言自明的，而糟糕的设计则让人困惑不已。',
    tags: ['读书', '设计', '心理学']
  },
  {
    id: 5,
    title: '关于时间的哲学思考',
    category: '哲学思辨',
    date: '2024-06-20',
    readTime: 15,
    views: 543,
    summary: '时间是什么？它是线性的还是循环的？我们能否真正"拥有"时间？这些问题困扰了哲学家数千年，而我也想分享一些个人的思考。',
    tags: ['哲学', '时间', '思考']
  }
]

export const hotArticles = [
  { id: 2, title: '从Vue2到Vue3：一次渐进式的迁移之旅' },
  { id: 1, title: '设计中的留白艺术：少即是多' },
  { id: 3, title: '夏日午后的咖啡馆' }
]

export const projects = [
  {
    id: 1,
    name: '个人博客系统',
    cover: 'https://picsum.photos/400/300?random=1',
    description: '基于Vue3 + Spring Boot的全栈博客系统',
    tags: ['Vue3', 'Spring Boot', 'MySQL'],
    github: 'https://github.com',
    demo: 'https://demo.com'
  },
  {
    id: 2,
    name: 'UI组件库',
    cover: 'https://picsum.photos/400/300?random=2',
    description: '一套简洁优雅的Vue3组件库',
    tags: ['Vue3', 'TypeScript', 'Sass'],
    github: 'https://github.com'
  },
  {
    id: 3,
    name: '任务管理工具',
    cover: 'https://picsum.photos/400/300?random=3',
    description: '简洁高效的个人任务管理应用',
    tags: ['React', 'Node.js', 'MongoDB'],
    demo: 'https://demo.com'
  }
]

export const archiveData = {
  '2024': {
    '七月': [
      { id: 1, title: '设计中的留白艺术：少即是多', date: '07-15' },
      { id: 2, title: '从Vue2到Vue3：一次渐进式的迁移之旅', date: '07-10' },
      { id: 3, title: '夏日午后的咖啡馆', date: '07-05' }
    ],
    '六月': [
      { id: 4, title: '《设计心理学》读后感', date: '06-28' },
      { id: 5, title: '关于时间的哲学思考', date: '06-20' }
    ]
  },
  '2023': {
    '十二月': [
      { id: 6, title: '年终总结：成长与反思', date: '12-31' }
    ]
  }
}

export const articleDetail = {
  id: 1,
  title: '设计中的留白艺术：少即是多',
  category: '设计沉思',
  date: '2024-07-15',
  readTime: 8,
  views: 1256,
  tags: ['设计', '留白', '用户体验'],
  content: `
## 什么是留白

留白（White Space），也称为负空间（Negative Space），是设计中未被内容填充的区域。它不一定是白色的，而是指任何没有文字、图像或其他视觉元素的空间。

## 留白的价值

### 1. 提升可读性

适当的留白可以让文字更易阅读。行间距、段落间距、页边距都是留白的体现。研究表明，合理的留白可以提升阅读理解能力高达20%。

### 2. 引导视觉焦点

> "设计不是添加更多元素，而是去除不必要的部分。" —— 约翰·前田

留白可以帮助用户聚焦于重要内容。当周围有足够的空间时，核心元素会更加突出。

### 3. 传递品牌调性

高端品牌往往大量使用留白，因为它传递出一种从容、优雅的气质。苹果、无印良品都是留白设计的典范。

## 如何运用留白

\`\`\`css
/* 示例：为内容区域添加呼吸感 */
.content {
  padding: 2rem 3rem;
  line-height: 1.8;
  max-width: 720px;
  margin: 0 auto;
}
\`\`\`

## 结语

留白是一种克制，也是一种智慧。在信息爆炸的时代，懂得留白的设计师，才能创造出真正打动人心的作品。
  `
}
