
/**
 * This configuration was generated using the CKEditor 5 Builder. You can modify it anytime using this link:
 * https://ckeditor.com/ckeditor-5/builder/?redirect=portal#installation/NoJgNARCB0Ds0A4KQKwgMy0wThARjxAAZsiiAWdc8/ELANnMLwXqKyPUOwXKUgAOAFwC0AIwBOyImGB4w8xQrBEAupARiAhkQBm9FBFVA===
 */

const {
	ClassicEditor,
	Autosave,
	Essentials,
	Paragraph,
	Mention,
	ImageUtils,
	ImageEditing,
	ImageBlock,
	ImageToolbar,
	ImageInline,
	ImageInsertViaUrl,
	AutoImage,
	CloudServices,
	ImageUpload,
	ImageStyle,
	ImageCaption,
	ImageTextAlternative,
	List,
	TodoList,
	Fullscreen,
	Emoji,
	Autoformat,
	TextTransformation,
	MediaEmbed,
	PasteFromMarkdownExperimental,
	Bold,
	Italic,
	Underline,
	Strikethrough,
	Code,
	Subscript,
	Superscript,
	FontBackgroundColor,
	FontColor,
	FontFamily,
	FontSize,
	Highlight,
	Heading,
	Link,
	AutoLink,
	BlockQuote,
	HorizontalLine,
	CodeBlock,
	Indent,
	IndentBlock,
	Alignment,
	Table,
	TableToolbar,
	PlainTableOutput,
	TableCaption,
	ShowBlocks,
	SourceEditing,
	GeneralHtmlSupport,
	HtmlComment,
	TextPartLanguage,
	Title,
	BalloonToolbar,
	BlockToolbar
} = window.CKEDITOR;

const LICENSE_KEY =
	'eyJhbGciOiJFUzI1NiJ9.eyJleHAiOjE3OTMzMTgzOTksImp0aSI6IjBmMjgxMzQyLWRmY2QtNDIyMS1iZWRlLTBmYjZlOTRkMGU5YiIsInVzYWdlRW5kcG9pbnQiOiJodHRwczovL3Byb3h5LWV2ZW50LmNrZWRpdG9yLmNvbSIsImRpc3RyaWJ1dGlvbkNoYW5uZWwiOlsiY2xvdWQiLCJkcnVwYWwiXSwiZmVhdHVyZXMiOlsiRFJVUCIsIkUyUCIsIkUyVyJdLCJyZW1vdmVGZWF0dXJlcyI6WyJQQiIsIlJGIiwiU0NIIiwiVENQIiwiVEwiLCJUQ1IiLCJJUiIsIlNVQSIsIkI2NEEiLCJMUCIsIkhFIiwiUkVEIiwiUEZPIiwiV0MiLCJGQVIiLCJCS00iLCJGUEgiLCJNUkUiXSwidmMiOiJiOGFjNmEzNSJ9.63EeG-0gf50voMi4cBoJrXHWuOH6ig8KBeEC0JqAWGDldQLckVZIjGvWgKG9aPjBUi7gSMAXDo5seVjBQ12e5g';

const editorConfig = {
	toolbar: {
		items: [
			'undo',
			'redo',
			'|',
			'sourceEditing',
			'showBlocks',
			'|',
			'heading',
			'|',
			'fontSize',
			'fontFamily',
			'fontColor',
			'fontBackgroundColor',
			'|',
			'bold',
			'italic',
			'underline',
			'|',
			'link',
			'insertTable',
			'highlight',
			'blockQuote',
			'codeBlock',
			'|',
			'alignment',
			'|',
			'bulletedList',
			'numberedList',
			'todoList',
			'outdent',
			'indent'
		],
		shouldNotGroupWhenFull: false
	},
	plugins: [
		Alignment,
		Autoformat,
		AutoImage,
		AutoLink,
		Autosave,
		BalloonToolbar,
		BlockQuote,
		BlockToolbar,
		Bold,
		CloudServices,
		Code,
		CodeBlock,
		Emoji,
		Essentials,
		FontBackgroundColor,
		FontColor,
		FontFamily,
		FontSize,
		Fullscreen,
		GeneralHtmlSupport,
		Heading,
		Highlight,
		HorizontalLine,
		HtmlComment,
		ImageBlock,
		ImageCaption,
		ImageEditing,
		ImageInline,
		ImageInsertViaUrl,
		ImageStyle,
		ImageTextAlternative,
		ImageToolbar,
		ImageUpload,
		ImageUtils,
		Indent,
		IndentBlock,
		Italic,
		Link,
		List,
		MediaEmbed,
		Mention,
		Paragraph,
		PasteFromMarkdownExperimental,
		PlainTableOutput,
		ShowBlocks,
		SourceEditing,
		Strikethrough,
		Subscript,
		Superscript,
		Table,
		TableCaption,
		TableToolbar,
		TextPartLanguage,
		TextTransformation,
		Title,
		TodoList,
		Underline
	],
	balloonToolbar: ['bold', 'italic', '|', 'link', '|', 'bulletedList', 'numberedList'],
	blockToolbar: [
		'fontSize',
		'fontColor',
		'fontBackgroundColor',
		'|',
		'bold',
		'italic',
		'|',
		'link',
		'insertTable',
		'|',
		'bulletedList',
		'numberedList',
		'outdent',
		'indent'
	],
	fontFamily: {
		supportAllValues: true
	},
	fontSize: {
		options: [10, 12, 14, 'default', 18, 20, 22],
		supportAllValues: true
	},
	fullscreen: {
		onEnterCallback: container =>
			container.classList.add(
				'editor-container',
				'editor-container_classic-editor',
				'editor-container_include-block-toolbar',
				'editor-container_include-fullscreen',
				'main-container'
			)
	},
	heading: {
		options: [
			{
				model: 'paragraph',
				title: 'Paragraph',
				class: 'ck-heading_paragraph'
			},
			{
				model: 'heading1',
				view: 'h1',
				title: 'Heading 1',
				class: 'ck-heading_heading1'
			},
			{
				model: 'heading2',
				view: 'h2',
				title: 'Heading 2',
				class: 'ck-heading_heading2'
			},
			{
				model: 'heading3',
				view: 'h3',
				title: 'Heading 3',
				class: 'ck-heading_heading3'
			},
			{
				model: 'heading4',
				view: 'h4',
				title: 'Heading 4',
				class: 'ck-heading_heading4'
			},
			{
				model: 'heading5',
				view: 'h5',
				title: 'Heading 5',
				class: 'ck-heading_heading5'
			},
			{
				model: 'heading6',
				view: 'h6',
				title: 'Heading 6',
				class: 'ck-heading_heading6'
			}
		]
	},
	htmlSupport: {
		allow: [
			{
				name: /^.*$/,
				styles: true,
				attributes: true,
				classes: true
			}
		]
	},
	image: {
		toolbar: ['toggleImageCaption', 'imageTextAlternative', '|', 'imageStyle:inline', 'imageStyle:wrapText', 'imageStyle:breakText']
	},
	initialData: '',
	language: 'pt-br',
	licenseKey: LICENSE_KEY,
	link: {
		addTargetToExternalLinks: true,
		defaultProtocol: 'https://',
		decorators: {
			toggleDownloadable: {
				mode: 'manual',
				label: 'Downloadable',
				attributes: {
					download: 'file'
				}
			}
		}
	},
	mention: {
		feeds: [
			{
				marker: '@',
				feed: [
					/* See: https://ckeditor.com/docs/ckeditor5/latest/features/mentions.html */
				]
			}
		]
	},
	menuBar: {
		isVisible: true
	},
	placeholder: '',
	table: {
		contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells']
	}
};

const form = document.getElementById('post-form');
const textarea = document.getElementById('body');
const editorElement = document.querySelector('#editor');

ClassicEditor.create(document.querySelector('#editor'), editorConfig)
    .then(editor => {
        // Se houver valor na textarea (por exemplo ao submeter com erros), usa-o como fonte principal.
        if (textarea.value && textarea.value.trim().length > 0) {
            editor.setData(textarea.value);
        }

        // Ao submeter, copia o conteúdo do editor para a textarea vinculada.
        form.addEventListener('submit', function () {
            textarea.value = editor.getData();
        });
    })
    .catch(error => {
        console.error('Erro ao inicializar o CKEditor:', error);
    });
