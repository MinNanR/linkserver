<template>
  <div style="margin-left:50px;margin-top:50px;">
    <div v-show="isEditing">
      <div style="margin-bottom:10px">
        <button class="btn btn-primary" style="margin-right:10px" @click="saveIntroduction">保存</button>
        <button class="btn btn-primary" @click="switchEditing()">取消</button>
      </div>

      <div>
        <quill-editor
          v-model="content"
          ref="editor"
          :options="editorOption"
          style="width:50%;height:500px;"
        ></quill-editor>
      </div>
    </div>
    <div v-show="!isEditing">
      <button class="btn btn-primary" style="margin-bottom:10px" @click="switchEditing()">编辑</button>
      <div v-html="introduction"></div>
    </div>
  </div>
</template>

<script>
import { quillEditor } from "vue-quill-editor";
import "quill/dist/quill.snow.css";
// import * as Quill from 'quill'

export default {
  components: {
    quillEditor,
  },
  data() {
    return {
      editor: null,
      content: `<p></p>`,
      editorOption: {
        theme: "snow",
        placehoder: "请输入正文",
      },
      isEditing: false,
      introduction: "",
    };
  },
  methods: {
    getIntroduction() {
      this.request
        .post("api/getIntroduction")
        .then((response) => {
          this.introduction = response.data.introduction;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    saveIntroduction() {
      this.request
        .post("manager/updateIntroduction", { content: this.content })
        .then((response) => {
          alert(response.message);
          this.getIntroduction();
          this.switchEditing();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    switchEditing() {
      this.isEditing = !this.isEditing;
      this.content = this.introduction;
    },
  },
  mounted() {
    this.editor = this.$refs.editor.quill;
    this.getIntroduction();
  },
};
</script>

<style>
</style>