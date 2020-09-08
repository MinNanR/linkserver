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
    <form>
      <input type="file" ref="upload" style="display:none" @input="selectFile()" />
    </form>
  </div>
</template>

<script>
import { quillEditor } from "vue-quill-editor";
import "quill/dist/quill.snow.css";
import axios from "axios";
// import * as Quill from 'quill'

const toolbarOptions = [
  ["bold", "italic", "underline", "strike"], // toggled buttons
  ["blockquote", "code-block"],

  [{ header: 1 }, { header: 2 }], // custom button values
  [{ list: "ordered" }, { list: "bullet" }],
  [{ script: "sub" }, { script: "super" }], // superscript/subscript
  [{ indent: "-1" }, { indent: "+1" }], // outdent/indent
  [{ direction: "rtl" }], // text direction

  [{ size: ["small", false, "large", "huge"] }], // custom dropdown
  [{ header: [1, 2, 3, 4, 5, 6, false] }],

  [{ color: [] }, { background: [] }], // dropdown with defaults from theme
  [{ font: [] }],
  [{ align: [] }],
  ["link", "image", "video"],
  ["clean"], // remove formatting button
];

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
        modules: {
          toolbar: {
            container: toolbarOptions,
            handlers: {
              image: this.uplaodImage,
            },
          },
        },
      },
      file: null,
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
    uplaodImage(value) {
      if (value) {
        let uploadInput = this.$refs.upload
        uploadInput.click()
      }
    },
    async selectFile() {
      let baseUrl = this.baseUrl;
      let formData = new FormData();
      let image = this.$refs.upload.files[0];
      formData.append("image", image);
      await axios({
        url: `${baseUrl}/manager/addImage`,
        data: formData,
        method: "post",
        headers: {
          "Content-Type": "multipart/form-data",
          authorization: localStorage.getItem("token"),
        },
      })
        .then((response) => {
          let url = response.data.data;
          let editor = this.$refs.editor.quill;
          let index = editor.selection.savedRange.index;
          editor.insertEmbed(index, "image", url);
          editor.setSelection(index + 1);
        })
        .catch((error) => {
          console.log(error);
        });
      this.formData = null;
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